obj-m += chardev.o
all:
	make -C /lib/modules/3.16.42-newlinux/build M=$(shell pwd) modules
clean:
	make -C /lib/modules/3.16.42-newlinux/build M=$(shell pwd) clean
