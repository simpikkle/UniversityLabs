package main.labs;

import main.threads.Consumer;
import main.threads.Printer;
import main.threads.Producer;
import main.threads.Warehouse;

public class Lab3 {

    private static Warehouse warehouse = new Warehouse();

    public static Warehouse getShared() {
        return warehouse;
    }

    public static void setShared(Warehouse shared) {
        warehouse = shared;
    }

    public static void main(String[] args) {
        int interactions = 10;
        Printer printer = System.out::println;
        Thread producer = new Producer(interactions, printer);
        Consumer consumer = new Consumer(interactions, printer);
        producer.setName("Producer");
        consumer.setName("Consumer");
        producer.start();
        consumer.start();
    }
}
