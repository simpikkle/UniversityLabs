package main.threads;

import java.util.Random;

import static main.labs.Lab3.getShared;

public class Producer extends Thread {

    private Printer printer;
    private int interactions = 0;
    private Random random = new Random();

    public Producer(int interactions, Printer printer) {
        this.interactions = interactions;
        this.printer = printer;
    }

    @Override
    public void run() {
        while (interactions-- > 0) {
            produce();
        }
        synchronized (getShared()) {
            getShared().setPosition(-1);
            printer.print(Thread.currentThread().getName() + " is completed.");
        }
    }

    private void produce() {
        synchronized (getShared()) {
            getShared().setPosition(random.nextInt(100));
            printer.print(Thread.currentThread().getName() + getShared());
            getShared().notify();
            try {
                getShared().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
