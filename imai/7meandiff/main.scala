import org.json4s._
import org.json4s.native.JsonMethods._
import scala.io.Source
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

case class IntegralOne(measured_at: ZonedDateTime, missing: Boolean, value: Double)
case class IntegralDiff(measured_at: ZonedDateTime, value: Double)


object Main {
  def convertFromJson(s: String) : List[IntegralOne] = {
    val ja = parse(s)
    for {
      JObject(child) <- ja
      JField("measured_at", JString(ms)) <- child
      JField("missing", JBool(m))        <- child
      JField("value", JDouble(v))        <- child
    } yield IntegralOne(ZonedDateTime.parse(ms), m, v)
  }
  def convertToDiffs(data: Seq[IntegralOne]) : Seq[Option[IntegralDiff]] =
    data.zip(data.tail).map(p =>
      if(p._1.missing || p._2.missing)
        None
      else
        Some(IntegralDiff(p._2.measured_at, p._2.value - p._1.value)))

  def calcScore(guessed: Seq[Double], actual: Seq[Double]) : Double = {
    assert(guessed.length == actual.length && guessed.length == 48)
    (guessed zip actual).map({ case (a,b) => (a-b)*(a-b) }).sum
  }

  def linearInterpolation(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    def linearSub(rest: List[IntegralOne], b1: Option[IntegralOne], b2: Option[IntegralOne], retrev: List[IntegralOne]) : List[IntegralOne] = {
      rest match {
        case Nil    => retrev.reverse
        case h :: t => {
          if(h.missing) {
            val r = t.indexWhere(!_.missing)
            val nh =
              if(r == -1) {
                (b1, b2) match {
                  case (_, None)          => sys.error("There is not enough data.")
                  case (None, _)          => sys.error("Maybe internal error.")
                  case (Some(a), Some(b)) => IntegralOne(h.measured_at, false, a.value + (a.value - b.value))
                }
              } else {
                val rv  = t(r).value
                  (b1, b2) match {
                  case (None, _)          => {
                    val r2 = t.indexWhere(!_.missing, r + 1)
                    if(r2 == -1)
                      sys.error("Not enough data")
                    else {
                      val r2v = t(r2).value
                      IntegralOne(h.measured_at, false, rv + (rv - r2v) * (1.0 * (r + 1) / (r2 - r)))
                    }
                  }
                  case (Some(a), _) =>
                    IntegralOne(h.measured_at, false, (rv + a.value * (r + 1)) / (r + 2))
                }
              }
            linearSub(t, Some(nh), b1, nh :: retrev)
          } else {
            linearSub(t, Some(h), b1, h :: retrev)
          }
        }
      }
    }
    linearSub(data.toList, None, None, List()).toSeq
  }
  def pushDummyIntegralOne(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    val h1 = data(0)
    val h2 = data(1)
    Seq(IntegralOne(h1.measured_at.minusMinutes(30), false, h1.value + (h1.value - h2.value))) ++ data
  }
  def addDiffs(start: IntegralOne, diffs: Seq[Double]) : Seq[IntegralOne] = {
    (1 to 48).map(r => {
                    val measured = start.measured_at.plusMinutes(30L * r)
                    val missing  = false
                    val value    = start.value + diffs.take(r).sum
                    IntegralOne(measured, missing, value)
                  })
  }

  def easyApplyDiff(data: Seq[IntegralOne], func: Seq[IntegralDiff] => Seq[Double]) : Seq[IntegralOne] = {
    val patched = pushDummyIntegralOne(linearInterpolation(data))
    val diff = convertToDiffs(patched).map(_.get) // there is no None.

    val last = data.last
    val expd = func(diff)
    addDiffs(last, expd)
  }


  // -----------------------------------------------------------------------------------------------
  // Just use yesterday's diff.
  def applyYesterdayDiff(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    easyApplyDiff(data, diff => diff.takeRight(48).map(_.value))
  }

  // Use 48 data which comes after similar occurrence to previous 48.
  def applySimilarNextDiff(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    def findSimilerDiff(diff: Seq[IntegralDiff]) : Seq[Double] = {
      val yesterday = diff.takeRight(48)
      val cands = (0 until (diff.length - 96)).map(s => {
                                                     val here = diff.slice(s, s + 48)
                                                     val next = diff.slice(s + 48, s + 96)
                                                     val scor = (here zip yesterday).map({ case (a,b) => Math.pow(a.value-b.value,2) }).sum
                                                     (scor, next)
                                                   })
      (cands.minBy(_._1))._2.map(_.value)
    }
    easyApplyDiff(data, findSimilerDiff)
  }

  // Use day data which comes after day similar to yesterday.
  def applySimilarDayDiff(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    def findSimilerDiff(diff: Seq[IntegralDiff]) : Seq[Double] = {
      val yesterday = diff.takeRight(48)
      val cands = (0 until (diff.length - 96) by 48).map(s => {
                                                           val here = diff.slice(s, s + 48)
                                                           val next = diff.slice(s + 48, s + 96)
                                                           val scor = (here zip yesterday).map({ case (a,b) => Math.pow(a.value-b.value,2) }).sum
                                                           (scor, next)
                                                         })
      (cands.minBy(_._1))._2.map(_.value)
    }
    easyApplyDiff(data, findSimilerDiff)
  }

  // Use mean value of data which has same day of the week.
  def applyMeanDayDiff(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    def findSimilerDiff(diff: Seq[IntegralDiff]) : Seq[Double] = {
      def getSameDayData(rest: Seq[IntegralDiff]) : Seq[Seq[IntegralDiff]] = {
        if(rest.length == 0)
          Nil
        else
          Seq(rest.takeRight(48)) ++ getSameDayData(rest.dropRight(48 * 7))
      }
      val days = getSameDayData(diff.dropRight(48 * 6))
      val dobs = days.map(_.map(_.value))
      val sums = dobs.fold(Seq.fill(48)(0.0))((l,r) => (l zip r).map({case (a: Double, b: Double) => a + b}))
      sums.map(c => c / (dobs.length))
    }
    easyApplyDiff(data, findSimilerDiff)
  }
  // Use previous value which has same day of the week
  def applyPreviousSameDayDiff(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    def findSimilerDiff(diff: Seq[IntegralDiff]) : Seq[Double] = {
      diff.dropRight(48 * 6).takeRight(48).map(_.value)
    }
    easyApplyDiff(data, findSimilerDiff)
  }


  // Use recent N diffs.
  def applyNMeanDiff(n: Int) : Seq[IntegralOne] => Seq[IntegralOne] = {
    def applyNMeanDiffAux(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
      def findSimilerDiff(diff: Seq[IntegralDiff]) : Seq[Double] = {
        val days = diff.grouped(48).toSeq
        val dobs = days.map(_.map(_.value)).takeRight(n)
        val sums = dobs.fold(Seq.fill(48)(0.0))((l,r) => (l zip r).map({case (a: Double, b: Double) => a + b}))
        sums.map(c => c / (dobs.length))
      }
      easyApplyDiff(data, findSimilerDiff)
    }
    applyNMeanDiffAux
  }

  // Use mean value of all the data.
  def applyAllMeanDiff(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    applyNMeanDiff(28)(data)
  }

  // Use mean value of all the data.
  // TODO: refactoring
  def apply7MeanDiff(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    applyNMeanDiff(7)(data)
  }

  // Employ algorithm which shows the best score.
  def pApplySearch(data: Seq[IntegralOne], algos: Seq[Seq[IntegralOne] => Seq[IntegralOne]]) : Seq[IntegralOne] = {
    val algos_score = algos.par.map(algo => {
                                      val score = scoreAlgorithm(data, algo, 7, 21)
                                      (algo, score)
                                    })
    (algos_score.minBy(_._2)._1)(data)
  }

  def applySearch1(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    val algos = List(
      applyYesterdayDiff _,
      applyAllMeanDiff _
    )
    pApplySearch(data, algos)
  }
  def applySearch2(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    val algos = List(
      applySimilarNextDiff _,
      applyYesterdayDiff _,
      apply7MeanDiff _
    )
    pApplySearch(data, algos)
  }
  // -----------------------------------------------------------------------------------------------

  def scoreAlgorithm(data: Seq[IntegralOne], algo: Seq[IntegralOne] => Seq[IntegralOne],
                     days: Int = 28, known: Int = 30) : Double = {
    val scores = (0 until days).map(dday => {
                                      val cursor  = dday * 48
                                      val ssize   = 48 * known
                                      val sample  = data.drop(cursor).take(ssize)
                                      // if there is missing data in answer, score is not valid.
                                      //  so I apply linear interpolation to answer.
                                      val answer  = linearInterpolation(data.drop(cursor + ssize).take(48))
                                      val guessed = algo(sample)
                                      calcScore(answer.map(_.value), guessed.map(_.value))
                                    })
    scores.sum
  }

  def getAlgos() : Seq[(String, Seq[IntegralOne] => Seq[IntegralOne])] =
    Seq(
      ("applyYesterdayDiff",       applyYesterdayDiff _),
      ("applySimilarDiff",         applySimilarNextDiff _),
      ("applySimilarDayDiff",      applySimilarDayDiff _),
      ("applyMeanDayDiff"   ,      applyMeanDayDiff _),
      ("applyPreviousSameDayDiff", applyPreviousSameDayDiff _),
      ("applyAllMeanDiff",         applyAllMeanDiff _),
      ("apply7MeanDiff",           apply7MeanDiff _),
      ("applySearch1",             applySearch1 _),
      ("applySearch2",             applySearch2 _)
    )
  // https://github.com/json4s/json4s
  def forSubmit(data: Seq[IntegralOne], algo: Seq[IntegralOne] => Seq[IntegralOne]) : Unit = {
    import org.json4s.JsonDSL.WithDouble._
    val output = algo(data)
    val mapa   = output.map(io => ("measured_at" -> io.measured_at.toString) ~ ("missing" -> io.missing) ~ ("value" -> io.value))
    println(compact(render(mapa)))
  }
}

object ForSubmit {
  import Main._
  def main(args: Array[String]) : Unit = {
    val forward = args(0)
    val instant = args(1)
    val forwardd = convertFromJson(Source.fromFile(forward).getLines().mkString("\n"))
    forSubmit(forwardd, apply7MeanDiff)
  }
}

object TestOne {
  import Main._
  def main(args: Array[String]) : Unit = {
    val strs = args.toList.map(f => Source.fromFile(f).getLines().mkString("\n"))
    val data = strs.flatMap(s => convertFromJson(s))
    println(scoreAlgorithm(data, apply7MeanDiff))
  }
}

object PrintScoreFromPresets {
  import Main._
  def main(args: Array[String]) : Unit = {
    val files = List(
      ("../samples3/201508_forward_integral_7.json" , "../samples3/201509_forward_integral_7.json" ),
      ("../samples3/201509_forward_integral_7.json" , "../samples3/201510_forward_integral_7.json" ),
      ("../samples3/201510_forward_integral_7.json" , "../samples3/201511_forward_integral_7.json" ),
      ("../samples3/201508_forward_integral_8.json" , "../samples3/201509_forward_integral_8.json" ),
      ("../samples3/201509_forward_integral_8.json" , "../samples3/201510_forward_integral_8.json" ),
      ("../samples3/201510_forward_integral_8.json" , "../samples3/201511_forward_integral_8.json" ),
      ("../samples3/201508_forward_integral_9.json" , "../samples3/201509_forward_integral_9.json" ),
      ("../samples3/201508_forward_integral_12.json", "../samples3/201509_forward_integral_12.json"),
      ("../samples3/201509_forward_integral_12.json", "../samples3/201510_forward_integral_12.json"),
      ("../samples3/201510_forward_integral_12.json", "../samples3/201511_forward_integral_12.json"),
      ("../samples3/201508_forward_integral_13.json", "../samples3/201509_forward_integral_13.json"),
      ("../samples3/201509_forward_integral_13.json", "../samples3/201510_forward_integral_13.json"),
      ("../samples3/201510_forward_integral_13.json", "../samples3/201511_forward_integral_13.json"),
      ("../samples3/201509_forward_integral_16.json", "../samples3/201510_forward_integral_16.json"),
      ("../samples3/201510_forward_integral_16.json", "../samples3/201511_forward_integral_16.json"),
      ("../samples3/201509_forward_integral_20.json", "../samples3/201510_forward_integral_20.json"),
      ("../samples3/201510_forward_integral_20.json", "../samples3/201511_forward_integral_20.json"),
      ("../samples3/201510_forward_integral_21.json", "../samples3/201511_forward_integral_21.json"),
      ("../samples3/201510_forward_integral_22.json", "../samples3/201511_forward_integral_22.json"),
      ("../samples3/201510_forward_integral_23.json", "../samples3/201511_forward_integral_23.json"),
      ("../samples3/201510_forward_integral_24.json", "../samples3/201511_forward_integral_24.json"),
      ("../samples3/201510_forward_integral_30.json", "../samples3/201511_forward_integral_30.json"),
      ("../samples3/201510_forward_integral_34.json", "../samples3/201511_forward_integral_34.json"),
      ("../samples3/201510_forward_integral_35.json", "../samples3/201511_forward_integral_35.json")
    )
    val datas = files.map({case (a, b) => {
                             val strs = List(a,b).map(f => Source.fromFile(f).getLines().mkString("\n"))
                             strs.flatMap(s => convertFromJson(s))
                           }})
    // (name, datas)
    val lines = getAlgos().foldLeft(Seq.fill(files.length+1)(""))((lines, name_and_algo) => {
                                                                    name_and_algo match {
                                                                      case (name, algo) => {
                                                                        val name_array = lines(0)
                                                                        val algo_lines = lines.tail
                                                                        val resu = datas.par.map(data => scoreAlgorithm(data, algo))
                                                                        Seq(name_array + (if(name_array != "") ", " else "") + name) ++
                                                                          (algo_lines zip resu).map({ case (line, res) =>
                                                                                                      line + (if(line != "") ", " else "") + res })
                                                                      }
                                                                    }
                                                                  })
    println(lines.mkString("\n"))
  }
}
