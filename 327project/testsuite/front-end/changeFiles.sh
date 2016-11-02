



name="expected"
for i in expected/*.txt
 do
  var=$i
  var=${var#*/}
  var=${var%.*}
  echo $var$name
  mv $i expected/$var$name.txt
  #counter=$((counter + 1))
 done
