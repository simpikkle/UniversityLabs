#!/bin/sh
rm /dev/chardev
mknod /dev/chardev c 100 0
make 
insmod chardev
echo "Test" > /dev/chardev
cat /dev/chardev
echo "msg_delete" > /dev/chardev
cat /dev/chardev
echo "Test2" > /dev/chardev
cat /dev/chardev
rmmod chardev
