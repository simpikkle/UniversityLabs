/*
* chardev.h - the header file with the ioctl definitions.
*
* The declarations here have to be in a header file, because
* they need to be known both to the kernel module
* (in chardev.c) and the process calling ioctl (ioctl.c)
*/
#ifndef CHARDEV_H
#define CHARDEV_H
#include <linux/ioctl.h>
/*
* The major device number. We can't rely on dynamic
* registration any more, because ioctls need to know
* it.
*/
#define MAJOR_NUM 100
/*
* Set the message of the device driver
*/
#define IOCTL_SET_MSG _IOR(MAJOR_NUM, 0, char *)
