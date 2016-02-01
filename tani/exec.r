#!/usr/bin/env Rscript

# This file is written by Tomoki Imai (not Takahisa Tani)
# This is used for automating execution.

source("IIJ.r")
est <- IIJ("integ.txt")
write(est, file="result.txt", ncolumns=1)
