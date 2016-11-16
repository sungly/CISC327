#!/bin/bash

#automates test cases and checks that each test case matches its expected output
#and checks that the conole output matches its expected console output

name="output"
expected="outputexpected"
n='
'
count=1
#clears the error.txt to keep track of current errors
echo ''>errors.txt

for i in input/*.txt
 do
   #clearss the summary.txt to avoid duplicates
  echo "" > summary.txt
  echo "running test $count"
  f="input/test$count.txt"
  ../../frontend/simbank validaccounts.txt summary.txt < $f > console.txt
  #cp summary.txt output/output$counter.txt
  var=$f
  var=${var#*/}
  var=${var%.*}
  cp summary.txt output/$var$name.txt
  cp console.txt cout/$var-cout.txt

  #compares the summary file with the expected output file
  if ! cmp summary.txt expected/$var$expected.txt
    then
    	echo "Error found in summary file of $var$n" >> errors.txt
  fi
  #compares the
  if ! cmp console.txt expected-cout/"$var-coutexpected.txt"
  	then
  		echo "Error found in console output of $var$n" >> errors.txt
  fi

  count=$((count + 1))
 done
