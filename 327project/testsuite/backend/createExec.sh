#!/bin/sh

javac ./Tests.java
echo "Main-Class: Tests" > MANIFEST.MF
jar -cvmf MANIFEST.MF Tests.jar *.class

cat ../../backend/stub.sh BackEnd.jar > backend && chmod +x backend
