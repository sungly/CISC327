



name="expected"
for i in cout/*.txt
 do
  var=$i
  var=${var#*/}
  var=${var%.*}
  echo $var$name
  cp $i expected-cout/$var$name.txt
  #counter=$((counter + 1))
 done
