package main.labs;

import main.threads.Printer;

import java.util.ArrayList;
import java.util.List;

public class Lab4 {

    private Printer printer;

    public Lab4(Printer printer) {
        this.printer = printer;
    }

    public static void main(String[] args) {
        int threadNumber = Integer.parseInt(args[0]);
        int rowNumber = Integer.parseInt(args[1]);

        new Lab4(System.out::print)
                .startThreads(threadNumber, rowNumber);
    }

    public void startThreads(int threadNumber, int rowNumber) {
        List<Task> threads = new ArrayList<>(threadNumber);

        for (int i = 0; i < threadNumber; i++) {
            threads.add(new Task(
                    i == 0 ? null : threads.get(i-1),
                    rowNumber, i, printer));
        }
        threads.get(0).next = threads.get(threadNumber-1);
        threads.get(threadNumber - 1).last = true;

        for (int i = threadNumber; i > 0; i--) {
            new Thread(threads.get(i-1)).start();
        }
    }

    private final static class Task implements Runnable {

        private Printer printer;

        private Object next;
        private int count;
        private int number;
        private boolean last = false;

        public Task(Object next, int count, int number, Printer printer) {
            this.next = next;
            this.count = count;
            this.number = number;

            this.printer = printer;
        }

        @Override
        public void run() {

            for (int i = 0; i < count; ++i) {
                if (next != null && !(number == 0 && i == 0))
                    synchronized (next) {
                        try {
                            next.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                printer.print(" Thread-" + number);
                if (last) {
                    printer.print("\n");
                }
                synchronized (this) {
                    this.notify();
                }
            }
        }
    }
}
