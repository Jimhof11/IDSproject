#!/bin/bash
read -p "Enter command to trace: " cmd

strace -o ~/IDSproject/Logs/"$cmd"Log.txt $cmd

echo "Log file created, ready to Parse." 
