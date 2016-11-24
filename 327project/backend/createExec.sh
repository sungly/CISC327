#!/bin/sh

javac BackEnd.java
echo "Main-Class: BackEnd" > MANIFEST.MF
jar -cvmf MANIFEST.MF BackEnd.jar *.class

cat stub.sh BackEnd.jar > backend && chmod +x backend
