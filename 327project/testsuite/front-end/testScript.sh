#!/bin/bash
#automates test cases and checks that each test case matches its expected output

name="output"
expected="outputexpected"
n='\n'

#clears the error.txt to keep track of current errors
echo ''>errors.txt

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

  #compares the summary file with the expected output file
  if ! cmp summary.txt expected/$var$expected.txt
    then
      echo $var$n >> errors.txt
  fi
 done
