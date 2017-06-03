#include <linux/kernel.h>
#include <linux/module.h>
#include <linux/interrupt.h>
#include <asm/io.h>

MODULE_LICENSE("GPL");

#define KBD_IRQ 1               /* IRQ number for keyboard (i8042) */
#define KBD_DATA 0x60           /* I/O port for keyboard data */
#define KBD_SCAN_CODE_MASK 0x7f
#define KBD_STATUS_MASK 0x80

static char scancode;

static irqreturn_t kbd2_isr(int irq, void *data)
{
    char *sc = (char *)data;

    *sc = inb(KBD_DATA); //Считываем состояние клавиатуры
    
    // irq_wake_thread - разбудить поток IRQ для действия определенных dev_id.
    return IRQ_WAKE_THREAD;
}

static irqreturn_t kbd2_thread(int irq, void *data)
{
    char sc = *(char *)data;
   
    //Записываем в alert лог
    pr_alert("### Scan Code %#x %s\n",
            sc & KBD_SCAN_CODE_MASK,
            sc & KBD_STATUS_MASK ? "Released" : "Pressed");
    
    //Значение IRQ_HANDLED означает, что устройство прерывания распознано как обслуживаемое обработчиком, и прерывание успешно обработано.
    return IRQ_HANDLED;
}

static int __init kbd2_init(void)
{
    /*
    Аргументы request_threaded_irq (объявляет новый обработчик прерываний):

    1. irq - Interrupt line to allocate
    2. handler - Функция вызываемая при прерывании. Основной обработчик для потока прерывания при существующем потоке обработчика
    3. thread_fn - Функция вызываемая с потока прерывания
    4. irqflags - Флаги
    5. devname - Имя для вызываемого устройства
    6. dev_id - Для передачи назад в функцию обработчика
    */
    return request_threaded_irq(KBD_IRQ, kbd2_isr, kbd2_thread,
            IRQF_SHARED, "falulty", &scancode);
    //IRQF_SHARED — разрешить разделение (совместное использование) линии IRQ с другими устройствами (PCI шина и устройства).
}

static void __exit kbd2_clean(void)
{
    //Остановить обработчик прерываний.
    free_irq(KBD_IRQ, &scancode);
}

module_init(kbd2_init);
module_exit(kbd2_clean);
