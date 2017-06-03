/*
 *  start.c - Пример модуля, исходный текст которого размещен в нескольких
 файлах */
#include <linux/kernel.h>
#include <linux/module.h>
int init_module(void)
/* Все-таки мы пишем код ядра! */
/* Необходим для любого модуля ядра */
{
    printk("Hello, world - this is the kernel speaking\n");
    return 0;
}
