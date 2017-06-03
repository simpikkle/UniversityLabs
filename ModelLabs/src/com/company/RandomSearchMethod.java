package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

public class RandomSearchMethod {

    private Point P = new Point(0.1, 0.1);
    double[] theoreticalY = new CF().getY();
    private int N = 1000;
    private double h = 0.05;
    private List<Point> visited = new ArrayList<>();
    double[] experimental = new CF().getY();

    private double minResult;

    public RandomSearchMethod(double noise) {
        experimental = new Randomizer(noise).makeSomeNoise(experimental);
    }

    public Point getP() {
        return P;
    }

    public void calculate() {
        int printCounter = 0;
        visited.add(P);
        System.out.println(P + String.format("\t%1.2f", getCurrentResult(P)));
        minResult = getCurrentResult(P);
        for (int i = 0; i < N; i++) {
            Point currentP = new Point();
            do {
                int direction = getRandomDirection();
                if (direction < 0) {
                    return;
                } else {
                    currentP.copyValues(P);
                    currentP.changeXYinDirection(direction, h);
                    P.getPointsToExclude().add(direction);
                }
            } while (pointIsVisited(currentP));
            visited.add(currentP);
            double currentResult = getCurrentResult(currentP);
            if (currentResult < minResult) {
                minResult = currentResult;
                P = currentP;
                if (printCounter++ % 1 == 0) {
                    System.out.println(P + String.format("\t%1.2f", minResult));
                }
            }
        }
        System.out.println(P + String.format("\t%1.2f", minResult));
    }

    private double getCurrentResult(Point currentP) {
        double result = 0.0;
        double[] theoretical = new CF(currentP.x(), currentP.y()).getY();
        for (int k = 0; k < experimental.length; k++) {
            result += Math.pow(experimental[k] - theoretical[k], 2);
        }
        return result;
    }

    private boolean pointIsVisited(Point point) {
        for (Point p : visited) {
            if (p.equals(point)) {
                return true;
            }
        }
        return false;
    }

    private int getRandomDirection() {
        Random random = new Random();
        int direction = random.nextInt(8) + 1;
        if (P.getPointsToExclude().size() >= 8) {
            return -1;
        } else {
            if (P.getPointsToExclude().contains(direction)) {
                direction = getRandomDirection();
            } else {
                return direction;
            }
        }
        return direction;
    }

    private double function1(Point point) {
        return 100*Math.pow((Math.pow(point.x(), 2)- point.y()),2)+(1-Math.pow(point.x(), 2));
    }

    private double function2(Point point) {
        int A = 5;
        int B = 5;
        return Math.pow(point.x() / A, 2) + Math.pow(point.y() / B, 2);
    }

}
