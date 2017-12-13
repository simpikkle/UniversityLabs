package optimization;

import optimization.domain.Functions;
import optimization.domain.Point;
import optimization.domain.PointAndFunction;

import java.util.Arrays;

public class NedlerMead implements Lab {

    private Point best;
    private Point good;
    private Point worse;

    private double expectedResult;
    private double precision;

    @Override
    public void run() {
        Point v0, v1, v2;
        // Square
//        v0 = new Point(0, 1);
//        v1 = v0.plus(new Point(0.05, 0.05));
//        v2 = v0.plus(new Point(0.05, 0.0025));
//        expectedResult = 0.0;
//        precision = 0.0000001;

        // Rosenbrok
//        v0 = new Point(-1.2, 1);
//        v1 = v0.plus(new Point(0.05, 0.05));
//        v2 = v0.plus(new Point(0.05, 0.0025));
//        expectedResult = 0.0;
//        precision = 0.000000000000001;

//        // Asymmetric valley
        v0 = new Point(0, -1);
        v1 = v0.plus(new Point(0.05, 0.05));
        v2 = v0.plus(new Point(0.05, 0.0025));
        expectedResult = 0.199786;
        precision = 0.00001;
//
//        // Pauell
//        v0 = new Point(new double[]{3, -1, 0, 1});
//        v1 = new Point(new double[]{1, 1, 1, 1});
//        v2 = new Point(new double[]{5, 5, 5, 5});
//        expectedResult = 0.0;
//        precision = 0.00001;
//
////         Experimental
//        v0 = new Point(new double[]{2.7, 90, 1500, 10});
//        v1 = new Point(new double[]{1, 1, 1, 1});
//        v2 = new Point(new double[]{1, 100, 1000, 50});
//        expectedResult = 0.0;
//        precision = 0.00001;

        assignBestGoodWorse(v0, v1, v2);
        runMethod();
    }

    private void runMethod() {
        int i = 0;
        while (i++ < 2048 && resultIsNotAchieved()) {
            assignBestGoodWorse(best, good, worse);
            Point middle = findMiddle();
            double alpha = 1;
            Point xr = middle.plus(middle.minus(worse).multiply(alpha));
            if (function(xr) < function(best)) {
                alpha *= 2;
                Point xe = middle.plus(xr.minus(middle).multiply(alpha));
                if (function(xe) < function(best)) {
                    worse = good;
                    good = best;
                    best = xe;
                } else {
                    worse = good;
                    good = best;
                    best = xr;
                }
            } else {
                alpha /= 2;
                Point xe = middle.plus(middle.minus(worse).multiply(alpha));
                if (function(xe) < function(best)) {
                    worse = good;
                    good = best;
                    best = xe;
                } else {
                    good = best.plus(good.minus(best).multiply(alpha));
                    worse = best.plus(worse.minus(best).multiply(alpha));
                }
            }
            System.out.println(String.format("Iteration: %d. Best: %s. Function: %1.5f.", i, best, function(best)));
        }
        System.out.println("Best point: " + best);
        System.out.println(String.format("Best function: %1.16f", function(best)));
    }

    private boolean resultIsNotAchieved() {
        return !(function(best) <= expectedResult + precision
                && function(best) >= expectedResult - precision);
    }

    private Point findMiddle() {
        return (best.plus(good)).derive(2);
    }

    private void assignBestGoodWorse(Point v0, Point v1, Point v2) {
        PointAndFunction y[] = new PointAndFunction[3];
        y[0] = new PointAndFunction(v0, function(v0));
        y[1] = new PointAndFunction(v1, function(v1));
        y[2] = new PointAndFunction(v2, function(v2));
        Arrays.sort(y);
        best = y[0].getPoint();
        good = y[1].getPoint();
        worse = y[2].getPoint();
    }

    private double function(Point point) {
//        return Functions.square(point);
//        return Functions.rosenbrok(point);
        return Functions.asymmetricValley(point);
//        return Functions.pauell(point);
//        return Functions.experimental(point);
    }
}
