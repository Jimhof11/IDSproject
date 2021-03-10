#!/bin/bash

counter=0
touch ~/IDSproject/Logs/csv/topdata.csv

#loop
while [ $counter -lt 30 ]
do

#time interval
counter=$(($counter+1))
read -p "Waiting for 20 sec..." -t 20

#get top data and add comma for csv format
top -b | head -n5 >> ~/IDSproject/Logs/csv/topdata.csv
echo "," >> ~/IDSproject/Logs/csv/topdata.csv

#counter message
echo "Interval $counter complete "

done

echo "Script Finished!"



