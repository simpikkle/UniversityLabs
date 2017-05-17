package main;

import javafx.scene.control.TextArea;
import main.labs.Lab1;
import main.labs.Lab2;
import main.labs.Lab4;
import main.threads.Consumer;
import main.threads.Printer;
import main.threads.Producer;

import java.util.Random;

public class Controller extends BaseController {

    @Override
    protected void onCalcAction() {
        Lab1 lab1 = new Lab1(expressionField.getCharacters().toString());
        answerField.setText(lab1.calculate());
    }

    @Override
    protected void onExecuteSecond() {
       Lab2 lab2 = new Lab2(new Random().nextInt(32));
       resultTwo.setText(lab2.allProducts());
       resultTwo.appendText(lab2.presentOnly());
    }

    @Override
    protected void onExecuteThird() {
        resultThree.setText("");
        int interactions = 10;
        Printer printer = new ControllerPrinter(resultThree);
        Thread producer = new Producer(interactions, printer);
        Consumer consumer = new Consumer(interactions, printer);
        producer.setName("Producer");
        consumer.setName("Consumer");
        producer.start();
        consumer.start();
    }

    @Override
    protected void onExecuteFourth() {
        resultFourth.setText("");
        int threads;
        int rows;
        try {
            threads = Integer.parseInt(nThreads.getText());
            rows = Integer.parseInt(nRows.getText());
        } catch (NumberFormatException e) {
            resultFourth.setText("Please, enter the correct values of threads and rows.");
            return;
        }
        if (rows <= 0 || threads <= 0) {
            resultFourth.setText("Funny. Executed zero times. Or less.");
            return;
        }
        new Lab4(new ControllerPrinter(resultFourth))
                .startThreads(threads, rows);
    }

    private class ControllerPrinter implements Printer {
        private final TextArea result;

        public ControllerPrinter(TextArea result) {
            this.result = result;
        }

        @Override
        public void print(String s) {
            synchronized (result) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result.appendText(s + " ");
            }
        }

    }
}
