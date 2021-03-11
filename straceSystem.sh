#!/bin/bash
read -p "Enter command to trace: " cmd

strace -to ~/IDSproject/Logs/"$cmd"Log.txt $cmd

echo "Log file created, ready to Parse." 
