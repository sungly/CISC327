#!/bin/bash

daysOfOperation=5
masterAccountsDir="masterAccountsPrintOuts/MasterAccountsDay"
for ((i=1;i<=daysOfOperation;i++));
  do
    echo "Running Day $i"
    #runs the daily script for one day
    sh ./daily.sh
    #runs the backend
    cd ./backend
    sh backend
    echo "Copying Current MasterAccounts file"
    cp "MasterAccounts.txt" $masterAccountsDir$i".txt"

  done
