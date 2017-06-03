package com.company;

import java.util.Random;

import static java.lang.Math.*;

class Randomizer {

    private Random random = new Random();
    private double[] randomValues;
    private double sum = 0;
    private double dy = 0.05; //0.1 //0.2

    Randomizer(double dy) {
        this.dy = dy;
        randomValues = new double[40];
        fillWithRandomValues();
    }

    private void fillWithRandomValues() {
        for (int i = 0; i < randomValues.length; i++) {
            randomValues[i] = getMullerMethodValue()*dy;
            sum += randomValues[i];
        }
    }

    private double getMullerMethodValue() {
        double r1 = (double)random.nextInt(98)/100+0.01;
        double r2 = (double)random.nextInt(98)/100+0.01;
        return sqrt(-2*log(r1))* cos(2*Math.PI*r2);
    }

    public double[] makeSomeNoise(double[] function) {
        double[] copy = new double[function.length];
        double max = findYMax(function);
        for (int i = 0; i < function.length; i++) {
            copy[i] = randomValues[i]*max + function[i];
        }
        return copy;
    }

    public void checkThatRandomizerIsCorrect() {
        printResults();
        sortArray();
        printAmountOfElementsPerPeriod();
    }

    private void sortArray() {
        int min;
        for (int out = 0; out < randomValues.length-1; out++) {
            min = out;
            for (int in = out+1; in < randomValues.length; in++) {
                if (randomValues[in] < randomValues[min]) {
                    min = in;
                }
            }
            swap(out, min);
        }
    }

    private double findYMax(double[] y) {
        double max = y[0];
        for (int i = 1; i < y.length-1; i++) {
            if (y[i] > max) {
                max = y[i];
            }
        }
        return max;
    }

    private void swap(int first, int second) {
        double temp = randomValues[first];
        randomValues[first] = randomValues[second];
        randomValues[second] = temp;
    }

    private void printResults() {
        System.out.println(String.format("\nThe sum is %5.2f", sum));
        System.out.println(String.format("\nThe average is %1.2f", sum/randomValues.length));
    }

    private void printAmountOfElementsPerPeriod() {
        int n = 10;
        double h = (randomValues[randomValues.length-1] - randomValues[0])/n;
        int count = 0;
        double period = randomValues[0]+h;
        for (int i = 0; i < randomValues.length; i++) {
            if (randomValues[i] <= period) {
                count++;
            } else {
                System.out.println(String.format("[%1.2f-%1.2f]\t%d",
                        period-h, period, count));
                //System.out.println(count);
                count = 0;
                period += h;
                continue;
            }
        }
    }

    public double[] getRandomValues() {
        return randomValues;
    }
}
