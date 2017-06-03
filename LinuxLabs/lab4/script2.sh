#!/bin/sh
program=$1
opt=$2
march=$3
loops=$4
for i in " " "-flto" "-fprofile-generate" "-flto -fprofile-generate"
do
	gcc $opt $march $loops $i $program -o test >> log2.txt 2>&1
	echo "Compiled with $opt $march $loops $i" >> log2.txt 2>&1
	time -p ./test >> log2.txt 2>&1
	du ./test >> log2.txt 2>&1
done
