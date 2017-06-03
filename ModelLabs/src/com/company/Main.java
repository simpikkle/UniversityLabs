package com.company;

import java.text.DecimalFormat;

public class Main {

    public static void main(String[] args) {
        calculateAndPrintAandB();
        //printExperimentalFunction(0.2);
    }

    private static void printExperimentalFunction(double noise) {
        double[] experimental = new Randomizer(noise).makeSomeNoise(new CF().getY());
        printResult(experimental);
    }

    private static void printResult(double[] resultFunction) {
        System.out.println("Result");
        for (int i = 0; i < 40; i++) {
            System.out.println(String.format("%d\t%1.2f",
                    i, resultFunction[i]));
        }
    }

    private static void calculateAndPrintAandB() {
        double avgA = 0;
        double avgB = 0;
        RandomSearchMethod rsm = new RandomSearchMethod(0.05);
        rsm.calculate();
        double a = rsm.getP().x();
        double b1 = rsm.getP().y();
        System.out.println("0.05: a = "+a+" b1 = "+b1);
        avgA += a;
        avgB += b1;
        rsm = new RandomSearchMethod(0.1);
        rsm.calculate();
        a = rsm.getP().x();
        b1 = rsm.getP().y();
        avgA += a;
        avgB += b1;
        System.out.println("0.1: a = "+a+" b1 = "+b1);
        rsm = new RandomSearchMethod(0.2);
        rsm.calculate();
        a = rsm.getP().x();
        b1 = rsm.getP().y();
        avgA += a;
        avgB += b1;
        System.out.println("0.2: a = "+a+" b1 = "+b1);

        System.out.println("\nAverage a = "+avgA/3 +" b = " + avgB/3);
        double[] resultFunction = new CF(avgA/3, avgB/3).getY();
        printResult(resultFunction);
    }
}