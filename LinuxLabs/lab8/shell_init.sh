#!/bin/sh

sudo mknod /dev/chardev c 60 0
sudo chmod 666 /dev/chardev

