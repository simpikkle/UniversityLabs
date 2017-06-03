#!/bin/sh

rmmod chardev
make
insmod chardev.ko
echo "Test String" > /dev/chardev
cat /dev/chardev
echo "delete" > /dev/chardev
cat /dev/chardev
echo "Test String2" > /dev/chardev
cat /dev/chardev
echo "--roll" > /dev/chardev
cat /dev/chardev
