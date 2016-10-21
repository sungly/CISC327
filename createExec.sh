#!/bin/sh

javac FrontEnd.java
echo "Main-Class: FrontEnd" > MANIFEST.MF
jar -cvmf MANIFEST.MF FrontEnd.jar *.class

cat stub.sh FrontEnd.jar > simbank.exe && chmod +x simbank.exe

