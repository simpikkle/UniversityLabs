
static ssize_t device_read(struct file *file, char __user * buffer, size_t length, loff_t * offset)
{
	int 	bytes_read = 0;
	//int 	MessageLength = 0;
	char 	MessageBuf[BUF_LEN];
	static char *MessageBuf_Ptr;
	int 	i = 0;
	char 	temp;

	#ifdef DEBUG
		printk(KERN_INFO "device_read(%p,%p,%d)\n", file, buffer, (int)length);
	#endif

	if (*Message_Ptr == 0){
		return 0;
	}

	if(DirectionOutput == 1){
		while (length && *Message_Ptr) {
			put_user(*(Message_Ptr++), buffer++);
			length--;
			bytes_read++;
		}
	}else{
		#ifdef DEBUG
			printk(KERN_INFO "Print string rollback");
		#endif

		strcpy(MessageBuf, Message);
		for(i = 0; i < strlen(MessageBuf)/2; i++){
			temp = MessageBuf[i];
			MessageBuf[i] = MessageBuf[ strlen(MessageBuf) - i - 1];
			MessageBuf[ strlen(MessageBuf) - i - 1] = temp;
		}
		MessageBuf_Ptr = MessageBuf;

		printk(KERN_INFO "String to out: %s", MessageBuf_Ptr);

		while (length && *MessageBuf_Ptr) {
			put_user(*(MessageBuf_Ptr++), buffer++);
			length--;
			bytes_read++;
		}
	}

	#ifdef DEBUG
		printk(KERN_INFO "Read %d bytes, %d left\n", bytes_read, (int)length);
	#endif

	return bytes_read;
}

static ssize_t device_write(struct file *file, const char __user * buffer, size_t length, loff_t * offset)
{
	int i;

	#ifdef DEBUG
		printk(KERN_INFO "device_write(%p,%s,%d)", file, buffer, (int)length);
	#endif

	for (i = 0; i < length && i < BUF_LEN; i++){
        get_user(MessageTemp[i], buffer + i);
	}

	if(strcmp(MessageDelete, MessageTemp) == 0){
		#ifdef DEBUG
			printk(KERN_INFO "Recieve Message delete command -> clear message");
		#endif
		memset(&Message[0], 0, sizeof(Message));
	}else{
		memset(&Message[0], 0, sizeof(Message));
		strcpy(Message, MessageTemp);
	}

	Message_Ptr = Message;

	return i;
}