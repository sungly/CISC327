#!/bin/bash

numTransactionSessions=10
transApplied=15
validAccounts="./validaccounts.txt"

for ((i=1;i<=numTransactionSessions;i++));
do
	echo "generate session $i"
	./generateSession.sh "./sessions/session$i" $transApplied
	printf "apply frontend to session $i\n---------------------\n"
	touch "./summaries/summary$i"
	./frontend/simbank $validAccounts "./summaries/summary$i" < "./sessions/session$i"
done;
echo "merge trans summaries"
./mergeSummaries.sh
echo "run back office"
#cd ./backend
#./backend
