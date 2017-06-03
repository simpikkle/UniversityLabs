package com.company;

import java.text.DecimalFormat;

public class CF {

    private double b1 = 0.8;
    private double a = 2;
    private double[] y = new double[40];

    public CF() {

    }

    public CF(double a, double b1) {
        this.a = a;
        this.b1 = b1;
    }

    public double[] getY() {
        calculateFunction(a, b1);
        return y;
    }

    private void calculateFunction(double a, double b1) {
        double  z1 = 0.0,
                z2 = 0.0,
                z3 = 0.0,
                dz1, dz2, dz3, function;
        int     b2 = 4,
                k = 3,
                x = 3;
        double h = 0.013;
        int counter = 0;
        int i = 0;
        for (int t = 0; t < 5000; t++) {
            dz1 = z1 + h*z2;
            dz2 = z2 + h*z3;
            dz3 = z3 + h*((x - (b2 + b2) * z3 - (b1 + a) * z2 - z1)/(a*b2));
            function = k * z1 - a * k * z2;
            if (t % 65 == 0) {
                if (i < 40) {
                    y[i] = function;
                    //System.out.println(String.format("%d\t%1.2f", i, y[i]));
                    i++;
                } else {
                    break;
                }
            }
            z1 = dz1;
            z2 = dz2;
            z3 = dz3;
        }
    }
}
