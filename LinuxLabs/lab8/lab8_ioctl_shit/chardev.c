
#include <linux/kernel.h> /* We're doing kernel work */
#include <linux/module.h> /* Specifically, a module */
#include <linux/fs.h>
#include <asm/uaccess.h> /* for get_user and put_user */




static int device_open( struct inode* inode, struct file* file)
{
  if (_device_Open)
      return -EBUSY;
  _device_Open++;
  try_module_get(THIS_MODULE);
  return SUCCESS;
}

static int device_release(struct inode* inode, struct file* file)
{
    _device_Open--;
    _deviceFlags &= ~DEVICE_FLAG_READ_COMPLITED;
    module_put(THIS_MODULE);
    return SUCCESS;
}

static ssize_t device_read(struct file* file, char __user* buffer, size_t length, loff_t* offset)
{
    size_t i, bytes_read = 0;

    if (_storedMessage[0] == '\0' || (_deviceFlags & DEVICE_FLAG_READ_COMPLITED)) {
        return 0;
    }
    if (_deviceFlags & DEVICE_FLAG_BACKWARD_READ) {
        for (i = _storedMessageLen - 1; i-- > 0;) {
            put_user(_storedMessage[i], buffer++);
            length--;
            bytes_read++;
        }
        put_user('\0', buffer++);
        length--;
        bytes_read++;
    }
    else {
        for (i = 0; i < _storedMessageLen; i++) {
            put_user(_storedMessage[i], buffer++);
            length--;
            bytes_read++;
        }
    }
    if (length && _deviceFlags & DEVICE_FLAG_ADD_NEW_LINE_ON_READ) {
        put_user('\n', buffer - 1);
        put_user('\0', buffer);
        length--;
        bytes_read++;
    }
    _deviceFlags |= DEVICE_FLAG_READ_COMPLITED;
    return bytes_read;
}

static ssize_t device_write(
    struct file* file, const char __user* buffer, size_t length, loff_t* offset)
{
    size_t i;
    char input_message[BUF_LEN];
    size_t message_len;

    message_len = min(length, (size_t)BUF_LEN);
    for (i = 0; i < message_len; i++) {
        get_user(input_message[i], buffer + i);
    }
    // check command
    if (strncmp(input_message, "--back", 6) == 0) {
        _deviceFlags |= DEVICE_FLAG_BACKWARD_READ;
    }
    else if (strncmp(input_message, "--forward", 9) == 0) {
        _deviceFlags &= ~DEVICE_FLAG_BACKWARD_READ;
    }
    else if (strncmp(input_message, "--remove", 7) == 0) {
        _storedMessage[0] = '\0';
    }
    else {
        _storedMessageLen = message_len;
        strlcpy(_storedMessage, input_message, message_len);
    }
    return i;
}

unsigned int ioctl_num, unsigned long ioctl_param)
{
    int i;
    char* temp;
    char ch;

    switch (ioctl_num) {
      case IOCTL_SET_MSG:
          temp = (char*)ioctl_param;
          get_user(ch, temp);
          for (i = 0; ch && i < BUF_LEN; i++, temp++)
              get_user(ch, temp);
          device_write(file, (char*)ioctl_param, i, 0);
          break;
      case IOCTL_GET_MSG:
          i = device_read(file, (char*)ioctl_param, 99, 0);
          put_user('\0', (char*)ioctl_param + i);
          break;
      case IOCTL_GET_NTH_BYTE:
          return _storedMessage[ioctl_param];
          break;
      case IOCTL_MSG_DELETE:
          device_write(file, (char*)'\0', BUF_LEN, 0);
          break;
    }
    return SUCCESS;
}

struct file_operations Fops = {.read = device_read,
    .write = device_write,
    .unlocked_ioctl = device_ioctl,
    .open = device_open,
    .release = device_release };

int init_module()
{
    int ret_val;

    ret_val = register_chrdev(MAJOR_NUM, DEVICE_NAME, &Fops);

    if (ret_val < 0) {
        printk(KERN_ALERT "%s failed with %d\n", "Sorry, registering the character device ", ret_val);
        return ret_val;
    }
    printk(KERN_INFO "%s The major device number is %d.\n", "Registeration is a success", MAJOR_NUM);
    printk(KERN_INFO "If you want to talk to the device driver,\n");
    printk(KERN_INFO "you'll have to create a device file. \n");
    printk(KERN_INFO "We suggest you use:\n");
    printk(KERN_INFO "mknod %s c %d 0\n", DEVICE_FILE_NAME, MAJOR_NUM);
    printk(KERN_INFO "The device file name is important, because\n");
    printk(KERN_INFO "the ioctl program assumes that's the\n");
    printk(KERN_INFO "file you'll use.\n");
    _deviceFlags |= DEVICE_FLAG_ADD_NEW_LINE_ON_READ;
    return 0;
}
{
    printk(KERN_INFO "Try unregister_chrdev, no check available now\n");
    unregister_chrdev(MAJOR_NUM, DEVICE_NAME);
}
