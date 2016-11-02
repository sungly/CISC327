#!/bin/bash

#../../simbank validaccounts.txt output/summary.txt < input/test2.txt
#counter=1
name="output"
for i in input/*.txt
 do
   #clearss the summary.txt to avoid duplicates
  echo "" > summary.txt
  echo "running test $i"
  ../../simbank validaccounts.txt summary.txt < $i
  #cp summary.txt output/output$counter.txt
  var=$i
  var=${var#*/}
  var=${var%.*}
  cp summary.txt output1/$var$name.txt
  #counter=$((counter + 1))
 done
