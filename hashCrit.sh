#!/bin/bash
read -p "Enter a Critical File to hash: " crit

echo "$crit" > temp.txt

cat "$0" | while read -r line; do
	printf %s "$line" | sha256sum | cut -f1 -d' ' 
	

done < $crit >> temp.txt

#sed 's/\n/,/g'    
sed ':a;N;$!ba;s/\n/,/g' temp.txt >> ~/IDSproject/Hashes/hashedCriticals.csv
rm temp.txt 
