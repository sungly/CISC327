#!/bin/sh

javac BackEnd.java
javac -d ../testsuite/backend BackEnd.java
echo "Main-Class: BackEnd" > MANIFEST.MF
jar -cvmf MANIFEST.MF BackEnd.jar *.class

cat stub.sh BackEnd.jar > backend && chmod +x backend
