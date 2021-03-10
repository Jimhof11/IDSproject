#!/bin/bash
#read parsed log file and what to filter it by: 
read -p "Enter the log to read:  " log
read -p "Enter column to search: " column
read -p "Enter value to filter by: " pattern

#grep headings from csv file
head -1 $log | cat > ~/IDSproject/Logs/filtered/filter"$pattern"log.csv

#find column number
#select case for column number
case $column in
Seq)
num=1 ;;
'Subject ID')
num=2 ;;
Action)
num=3 ;;
Share)
num=4 ;;
PathRoot)
num=5 ;;
PathMid)
num=6 ;;
PathTail)
num=7 ;;
fileName)
num=8 ;;
fileType)
num=9 ;;
Bytes)
num=10 ;;
'Start Hr')
num=11 ;;
'Start Min')
num=12 ;;
'Start Sec')
num=13 ;;
'Finish Hr')
num=14 ;;
'Finish Min')
num=15 ;;
'Finish Sec')
num=16 ;;
*)
echo "Invalid Column entered." ;;
esac 

#print rows based on search, output to csv file named by search pattern
awk -F ',' '$'$num' ~ /'$pattern'/ { print $0 }' < $log | cat >> ~/IDSproject/Logs/filtered/filter"$pattern"log.csv 

echo "Log file Updated/Created."

echo "Script Finished."
