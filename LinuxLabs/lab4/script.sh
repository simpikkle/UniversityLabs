#!/bin/sh
program=$1
for i in "-O0" "-Os" "-O1" "-O2" "-O3" "-O2 -march=native" "-O3 -march=native" "-O2 -march=native -funroll-loops" "-O3 -march=native -funroll-loops"
do
	gcc $i $program -o test >> log.txt 2>&1
	echo "Compiled with $i" >> log.txt 2>&1
	time -p ./test >> log.txt 2>&1
	du ./test >> log.txt 2>&1 
done


