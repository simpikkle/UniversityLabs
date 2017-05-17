package main.threads;

import static main.labs.Lab3.getShared;

public class Consumer extends Thread {

    private int interactions = 0;
    private int previousValue = 0;

    private Printer printer;

    public Consumer(Integer interactions, Printer printer) {
        this.interactions = interactions;
        this.printer = printer;
    }

    @Override
    public void run() {
        while (interactions-- > 0) {
            consume();
        }
        printer.print(Thread.currentThread().getName() + " is completed.");
    }

    private void consume() {
        synchronized (getShared()) {
            while (getShared().getPosition() == previousValue) {
                if (previousValue < 0 ) {
                    return;
                }
                try {
                    getShared().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
                previousValue = getShared().getPosition();
                printer.print(Thread.currentThread().getName() + getShared());
                getShared().notify();
            }
        }
    }
