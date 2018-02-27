public class DeadLocker {

    String someString = new String();
    String anotherString = new String();

    Thread firstThread = new Thread("My Thread 1"){
        public void run(){
            while(true){
                synchronized(someString){
                    synchronized(anotherString){
                        System.out.println(someString + anotherString);
                    }
                }
            }
        }
    };

    Thread secondThread = new Thread("My Thread 2"){
        public void run(){
            while(true){
                synchronized(anotherString){
                    synchronized(someString){
                        System.out.println(anotherString + someString);
                    }
                }
            }
        }
    };

    public static void main(String a[]){
        DeadLocker deadLocker = new DeadLocker();
        deadLocker.firstThread.start();
        deadLocker.secondThread.start();
    }
}
