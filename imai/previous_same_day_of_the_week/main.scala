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
  def applyNArrayDiff(before_days: Seq[Int]) : Seq[IntegralOne] => Seq[IntegralOne] =
    applyNArrayDiffWithWeight(before_days, Seq.fill(before_days.length)(1))

  def applyNArrayDiffWithWeight(before_days: Seq[Int], weight: Seq[Int]) : Seq[IntegralOne] => Seq[IntegralOne] = {
    assert(before_days.length == weight.length)
    (data: Seq[IntegralOne]) => {
      def getDiffs(diff: Seq[IntegralDiff]) : Seq[Double] = {
        val days = diff.grouped(48).toSeq
        val uses = (before_days zip weight).filter({case (d, w) => d <= days.length})
        val dobs = uses.map({case (d, w) => days(days.length-d).map(_.value * w)})
        val sums = dobs.fold(Seq.fill(48)(0.0))((l, r) => (l, r).zipped.map(_ + _))
        sums.map(c => c / uses.map(_._2).sum)
      }
      easyApplyDiff(data, getDiffs)
    }
  }

  def applyNdayBeforeDiff(n: Int) : Seq[IntegralOne] => Seq[IntegralOne] =
    applyNArrayDiff(Seq(n))

  // Just use yesterday's diff.
  def applyYesterdayDiff(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    applyNdayBeforeDiff(1)(data)
  }
  // Use previous value which has same day of the week
  def applyPreviousSameDayDiff(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    applyNdayBeforeDiff(7)(data)
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
  def applyMeanDayDiff(data: Seq[IntegralOne]) : Seq[IntegralOne] =
    applyNArrayDiff(Seq(7, 14, 21, 28))(data)


  // Use recent N diffs.
  def applyNMeanDiff(n: Int) : Seq[IntegralOne] => Seq[IntegralOne] =
    applyNArrayDiff(1 to n)

  // Use mean value of all the data.
  def applyAllMeanDiff(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    applyNMeanDiff(28)(data)
  }

  def apply7MeanDiff(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    applyNMeanDiff(7)(data)
  }
  def apply7MeanDiff2(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    applyNArrayDiffWithWeight(1 to 7, Seq(3,1,1,1,1,1,1))(data)
  }


  // Employ algorithm which shows the best score.
  def pApplySearch(algos: Seq[Seq[IntegralOne] => Seq[IntegralOne]]) : Seq[IntegralOne] => Seq[IntegralOne] = {
    (data: Seq[IntegralOne]) => {
      val algos_score = algos.map(algo => {
                                    val score = scoreAlgorithmTotal(data, algo, 7, 21)
                                    (algo, score)
                                  })
      (algos_score.minBy(_._2)._1)(data)
    }
  }
  // Employ algorithm which shows the best score.
  def pApplySearch2(algos: Seq[Seq[IntegralOne] => Seq[IntegralOne]],
                    useday: Int, known: Int) : Seq[IntegralOne] => Seq[IntegralOne] = {
    (data: Seq[IntegralOne]) => {
      val algos_score = algos.map(algo => {
                                    val score = scoreAlgorithmTotal(data, algo, useday, known)
                                    (algo, score)
                                  })
      (algos_score.minBy(_._2)._1)(data)
    }
  }

  def applySearch1(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    val algos = List(
      applyYesterdayDiff _,
      applyAllMeanDiff _
    )
    pApplySearch(algos)(data)
  }
  def applySearch2(data: Seq[IntegralOne]) : Seq[IntegralOne] = {
    val algos = List(
      applyPreviousSameDayDiff _,
      apply7MeanDiff _
    )
    pApplySearch2(algos, 3, 21)(data)
  }
  // -----------------------------------------------------------------------------------------------
  def scoreAlgorithmForEachDay(data: Seq[IntegralOne], algo: Seq[IntegralOne] => Seq[IntegralOne],
                               days: Int = 28, known: Int = 30) : Seq[Double] = {
    val scores = (0 until days).map(dday => {
                                      val removed = data.dropRight(48 * dday)
                                      val answer  = linearInterpolation(removed.takeRight(48))
                                      val sample  = removed.dropRight(48).takeRight(48 * known)
                                      // if there is missing data in answer, score is not valid.
                                      //  so I apply linear interpolation to answer.
                                      val guessed = algo(sample)
                                      calcScore(answer.map(_.value), guessed.map(_.value))
                                    })
    scores
  }
  def scoreAlgorithmTotal(data: Seq[IntegralOne], algo: Seq[IntegralOne] => Seq[IntegralOne],
                     days: Int = 28, known: Int = 30) : Double = {
    scoreAlgorithmForEachDay(data, algo, days, known).sum
  }

  def getAlgos() : Seq[(String, Seq[IntegralOne] => Seq[IntegralOne])] = {
    Seq(
      ("applyYesterdayDiff",       applyYesterdayDiff _),
      ("applySimilarDiff",         applySimilarNextDiff _),
      ("applySimilarDayDiff",      applySimilarDayDiff _),
      ("applyMeanDayDiff"   ,      applyMeanDayDiff _),
      ("applyPreviousSameDayDiff", applyPreviousSameDayDiff _),
      ("applyAllMeanDiff",         applyAllMeanDiff _),
      ("apply7MeanDiff",           apply7MeanDiff _),
      ("apply7MeanDiff2",          applyNArrayDiffWithWeight(1 to 7, Seq(2,1,1,1,1,1,1))),
      ("applySearch1",             applySearch1 _),
      ("applySearch2",             applySearch2 _)
    )
  }
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
    forSubmit(forwardd, applyPreviousSameDayDiff)
  }
}

object TestOne {
  import Main._
  def main(args: Array[String]) : Unit = {
    val strs = args.toList.map(f => Source.fromFile(f).getLines().mkString("\n"))
    val data = strs.flatMap(s => convertFromJson(s))
    println(scoreAlgorithmTotal(data, apply7MeanDiff))
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
      // ("../samples3/201510_forward_integral_34.json", "../samples3/201511_forward_integral_34.json"),
      ("../samples3/201510_forward_integral_35.json", "../samples3/201511_forward_integral_35.json")
    )
    val datas = files.map({case (a, b) => {
                             val strs = List(a,b).map(f => Source.fromFile(f).getLines().mkString("\n"))
                             strs.flatMap(s => convertFromJson(s))
                           }})
    val name_and_vals = getAlgos().par.map({case (name, algo) => (name, datas.flatMap(data => scoreAlgorithmForEachDay(data, algo)))})
    val vals_length = name_and_vals.head._2.length
    val lines = name_and_vals.foldLeft(Seq.fill(vals_length+1)(""))((lines, name_res) => {
                                                                      name_res match {
                                                                        case (name, vals) => {
                                                                          val name_array = lines(0)
                                                                          val algo_lines = lines.tail
                                                                          Seq(name_array + (if(name_array != "") ", " else "") + name) ++
                                                                            (algo_lines zip vals).map({ case (line, res) =>
                                                                                                                line + (if(line != "") ", " else "") + res })
                                                                        }
                                                                      }
                                                                    })
    println(lines.mkString("\n"))
    // val lines = getAlgos().par.map({
    //                                  case (name, algo) => {
    //                                    val resu = datas.map(data => scoreAlgorithmTotal(data, algo))
    //                                    (name, resu)
    //                                  }}).foldLeft(Seq.fill(files.length+1)(""))((lines, name_res) => {
    //                                                                               name_res match {
    //                                                                                 case (name, vals) => {
    //                                                                                   val name_array = lines(0)
    //                                                                                   val algo_lines = lines.tail
    //                                                                                   Seq(name_array + (if(name_array != "") ", " else "") + name) ++
    //                                                                                     (algo_lines zip vals).map({ case (line, res) =>
    //                                                                                                                 line + (if(line != "") ", " else "") + res })
    //                                                                                 }
    //                                                                               }
    //                                                                             })
    // println(lines.mkString("\n"))
  }
}
