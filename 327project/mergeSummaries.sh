#!/bin/bash

#Takes all summary files stored in ./summaries directory and merges them into a single
#merged transaction summary file in ./backend directory

#path of the file to create
mergedFile="./backend/MergedTransactions.txt"
#string to put at the end of the merged summary file
endFileString="ES 00000000 00000000 000 ***"

printf "" > $mergedFile
for file in ./summaries/*; do

	while IFS=$'\n' read -r line_data; do
		printf "$line_data\n" >> $mergedFile
	done < $file	
done

printf "$endFileString" >> $mergedFile
