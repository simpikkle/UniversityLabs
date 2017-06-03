package com.company;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Point {

    private double x = 1;
    private double y = 1;

    public Point() {

    }

    @Override
    public boolean equals(Object obj) {
        Point point = (Point)obj;
        return (point.x() == x) && (point.y() == y);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        String formattedX = new DecimalFormat("#0.000").format(x);
        String formattedY = new DecimalFormat("#0.000").format(y);
        return formattedX + "\t" + formattedY;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point copyValues(Point sourcePoint) {
        this.x = sourcePoint.x;
        this.y = sourcePoint.y;
        return this;
    }

    private List<Integer> pointsToExclude = new ArrayList<>(8);

    public List<Integer> getPointsToExclude() {
        return pointsToExclude;
    }

    public void changeXYinDirection(int direction, double h) {
        switch (direction) {
            case 1: y-=h; break;
            case 2: x-=h; break;
            case 3: x-=h; y-=h; break;
            case 4: x+=h; y-=h; break;
            case 5: x-=h; y+=h; break;
            case 6: x+=h; break;
            case 7: y+=h; break;
            case 8: x+=h; y+=h; break;
        }
    }
}
