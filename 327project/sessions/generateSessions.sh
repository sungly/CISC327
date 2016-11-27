#!/bin/bash

#Must be run with root privelages
#This script only generates transactions with legal input
#Takes two arguments, number of transactions to apply and session file to store them in
output=$1
#number of transactions to apply
numtrans=$2

transaction=(deposit withdraw transfer create delete)
sessionTypes=(atm agent)
maxAmounts=(100000 99999999)
numSessions=$((${#sessionTypes[@]}))
numTrans=$((${#transaction[@]}))

#get valid accounts
declare -a accNums
numAccs=0
while IFS=$'\n' read -r line_data; do
	accNums[numAccs]="${line_data}"
	((++numAccs))
done < "../validaccounts.txt"


#Choose session type
i=$(($RANDOM%numSessions))
sessionType=${sessionTypes[i]}

#get respective session types max amount for transactions
max=${maxAmounts[i]}

#if session is atm, set num transactions to 3
if [ "$sessionType" = "atm" ]; then
	numTrans=3
fi
printf "login\n$sessionType\n">$output
for i in `seq 1 $numtrans`;
do
	#randomly choose a transaction to do
	j=$(($RANDOM%numTrans))
	selTrans=${transaction[j]}
	#randomly choose an account to apply transaction
	k=$(($RANDOM%($numAccs-1)))
	acc=${accNums[k]}
	if (( "$j" > "2" )); then
		#if create must make account not in valid accounts
		if [ "$j" = "3" ]; then
			loop=true;
			while $loop; do
				acc=0
				loop=false
				for x in `seq 1 8`;
				do
					acc=$((acc*10+($RANDOM%9+1)))
					
				done
				for element in "${accNums[@]}";
				do
					if [ "$element" = "$acc" ]; then
						loop=true
					fi
				done
			done
		fi
		nameLength=$(($RANDOM%28+3))
		name=$(cat /dev/urandom | tr -cd 'a-f0-9' | head -c $nameLength)
		#create a random name using nameLength number of alphanumberic characters
		printf "$selTrans\n$acc\n$name\n" >> $output
	else
		#the transaction is either deposit withdraw or transfer
		#randomly choose an account to apply transaction
		k=$(($RANDOM%($numAccs-1)))
		input="$selTrans\n${accNums[k]}\n"
		if [ "$j" -eq "2" ]; then
			l=$(($RANDOM%($numAccs-1)))
			while [ $l -eq $k ]; do
				l=$(($RANDOM%($numAccs-1)))
			done
			input="$input${accNums[l]}\n"
		fi
		amnt=$(($RANDOM%(max-1)+1))
		input="$input$amnt\n"
		printf "$input" >> $output
	fi
done
printf "logout" >> $output