#!/bin/bash

#../../simbank validaccounts.txt output/summary.txt < input/test2.txt
counter=1
for i in input/*.txt
 do 
  echo "running test $i"
  ../../simbank validaccounts.txt summary.txt < $i
  cp summary.txt output/output$counter.txt
  counter=$((counter + 1))
 done

