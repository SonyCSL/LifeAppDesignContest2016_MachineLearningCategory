#!/usr/bin/env python


import sys
import subprocess
import json
import shlex
import tempfile
import os
from io import StringIO

NUMBER_OF_DAYS_IN_MONTH = 31
PYTHON_BIN = "python3"
# NOTE: Nobody uses instantaneous data.
#  Imai's program takes it as argument, but it does not use that data

# -- helper functions --

# returns [string] which contains two elements.
#  They will be used as argument of unsimplify.
def use_simplify_integral(input_json, output_filepath):
    with open(output_filepath, "w") as out:
        with tempfile.NamedTemporaryFile("w") as inp:
            with tempfile.NamedTemporaryFile("w") as err:
                # Simplify_Integral cannot recognize indented jsons.
                #  ... and order of values is important.
                # {"measured_at":"2015-10-31T23:00:00.000+09:00","missing":false,"value":2147.25}
                json.dump(input_json, inp, separators=(',', ':'), sort_keys=True)
                inp.flush()
                # subprocess.call requires fileno, so we cannot use StringIO
                subprocess.call(["java", "Simplify_Integral", inp.name], stdout=out, stderr=err)
                # FIXME: dirty hack. I think we should not use temporary file like this.
                # ret is like this -> 'Unsimplify : java Unsimplify_Integral 1510.28 1441031400000'
                # In this case, it returns ["1510.28", "1441031400000"]
                ret = open(err.name).read().strip().splitlines()[-1]
                splitted = ret.split(" ")
                return [splitted[4], splitted[5]]

# return json which contains one day data.
def use_unsimplify_integral(input_filepath, argument_provided_by_simplify):
    with open(input_filepath, "r") as inp:
        # FIXME: We should use Popen.communicate.
        with tempfile.NamedTemporaryFile("w") as out:
            # subprocess.call requires fileno, so we cannot use StringIO
            subprocess.call(["java", "Unsimplify_Integral"] + argument_provided_by_simplify, stdin=inp, stdout=out)
            return json.load(open(out.name))

def run_processing_program(input_json, project_path, input_filename, output_filename):
    input_path  = os.path.join(project_path, input_filename)
    output_path = os.path.join(project_path, output_filename)
    argument_for_unsimplify = use_simplify_integral(input_json, input_path)
    subprocess.call(shlex.split("processing-java --sketch=" + project_path + " --run"))
    return use_unsimplify_integral(output_path, argument_for_unsimplify)



# All functions takes one dictionary and output dictionary
# arg[0]: data for one month
# out:    data for next day
def furukakoi(input_json):
    return run_processing_program(input_json,
                                  os.path.join("furukakoi","AverageHourly"),
                                  "integ.txt",
                                  "ResultAve.txt")

def sagara(input_json):
    return run_processing_program(input_json,
                                  os.path.join("sagara","Centervalue"),
                                  "integ.txt",
                                  "Resultcenter.txt")

def tobaru(input_json):
    return run_processing_program(input_json,
                                  os.path.join("tobaru","life_design_t"),
                                  "integ.txt",
                                  "result.txt")

def machimura(input_json):
    with tempfile.NamedTemporaryFile("w") as inp:
        with tempfile.NamedTemporaryFile("w") as out:
            json.dump(input_json, inp, separators=(',', ':'))
            inp.flush()
            subprocess.call([PYTHON_BIN, os.path.join("machimura", "lifeDesign.py"), inp.name], stdout=out)
            return json.load(open(out.name))


def tokuhisaAux(input_json, proj_name):
    input_path  = os.path.join(proj_name, "integ.txt")
    output_path = os.path.join(proj_name, "result.txt")
    argument_for_unsimplify = use_simplify_integral(input_json, input_path)
    subprocess.call([PYTHON_BIN, "predict.py"], cwd=proj_name)
    return use_unsimplify_integral(output_path, argument_for_unsimplify)

def tokuhisa1(input_json):
    return tokuhisaAux(input_json, "tokuhisa1")

def tokuhisa2(input_json):
    return tokuhisaAux(input_json, "tokuhisa2")


def tani(input_json):
    input_path  = os.path.join("tani", "integ.txt")
    output_path = os.path.join("tani", "result.txt")
    argument_for_unsimplify = use_simplify_integral(input_json, input_path)
    subprocess.call(["Rscript", "exec.r"], cwd="tani")
    return use_unsimplify_integral(output_path, argument_for_unsimplify)

def imaiAux(input_json, proj_dir):
    with tempfile.NamedTemporaryFile("w") as inp:
        with tempfile.NamedTemporaryFile("w") as out:
            json.dump(input_json, inp, separators=(',', ':'))
            inp.flush()
            # ignore instantaneous.json
            subprocess.call(shlex.split("sbt --error 'set showSuccess := false' \"run-main ForSubmit " + inp.name + ' null.json"'),
                            stdout=out, cwd=proj_dir)
            return json.load(open(out.name))

def imai7meandiff(input_json):
    return imaiAux(input_json, os.path.join("imai", "7meandiff"))

def imaimean_one_month(input_json):
    return imaiAux(input_json, os.path.join("imai", "mean_one_month"))

def imaimean_previous_week2(input_json):
    return imaiAux(input_json, os.path.join("imai", "mean_previous_week2"))

def imaimean_same_day_of_the_week(input_json):
    return imaiAux(input_json, os.path.join("imai", "mean_same_day_of_the_week"))

def imaiprevious_same_day_of_the_week(input_json):
    return imaiAux(input_json, os.path.join("imai", "previous_same_day_of_the_week"))

def imaisimiler24(input_json):
    return imaiAux(input_json, os.path.join("imai", "similer24"))

def imaisimiler24day(input_json):
    return imaiAux(input_json, os.path.join("imai", "similer24day"))

def imaiyesterday(input_json):
    return imaiAux(input_json, os.path.join("imai", "yesterday"))


def main():
    if(len(sys.argv) == 1):
        print("usage: " + PYTHON_BIN + " count.py 1.json 2.json 3.json ...")
        return 1

    inputs = sys.argv[1:]
    # (name, algorithm)
    algorithms = [
        ("Furukakoi"                        , furukakoi),
        ("Sagara"                           , sagara),
        ("Tobaru"                           , tobaru),
        ("Machimura"                        , machimura),
        ("Tokuhisa1"                        , tokuhisa1),
        ("Tokuhisa2"                        , tokuhisa2),
        ("Tani"                             , tani),
        ("Imai7meandiff"                    , imai7meandiff),
        # ("Imaimean_one_month"               , imaimean_one_month), # I found this is same as previous_week2
        ("Imaimean_previous_week2"          , imaimean_previous_week2),
        ("Imaimean_same_day_of_the_week"    , imaimean_same_day_of_the_week),
        ("Imaiprevious_same_day_of_the_week", imaiprevious_same_day_of_the_week),
        # ("Imaisimiler24"                    , imaisimiler24),   # It causes compile error.
        ("Imaisimiler24day"                 , imaisimiler24day),
        ("Imaiyesterday"                    , imaiyesterday)
    ]

    inputs = [json.load(open(i)) for i in inputs]

    summary = {}
    for algorithm in algorithms:
        name = algorithm[0]
        func = algorithm[1]

        sq_errors = []
        for data in inputs:
            for slide in range(6, -1, -1):
                window   = data[-48 * (NUMBER_OF_DAYS_IN_MONTH+1+slide):][:(NUMBER_OF_DAYS_IN_MONTH+1) * 48]
                onemonth = window[:-48]
                expected = window[-48:]
                answer   = func(onemonth)

                sqe = 0
                assert len(expected) == len(answer)
                for i in range(len(expected)):
                    if(expected[i]["missing"] or answer[i]["missing"]):
                        print("Ignore missing field.")
                    else:
                        sqe += (expected[i]["value"] - answer[i]["value"]) ** 2

                sq_errors.append(sqe)
        summary[name] = sq_errors
        print(name, sq_errors, sum(sq_errors))

    print("------------------------------------------")
    for name in summary:
        print(name, summary[name])
    print("------------------------------------------")
    # sort.
    for k, v in sorted(summary.items(), key=lambda x: sum(x[1])):
        print(k, sum(v))
if __name__ == "__main__":
    main()
