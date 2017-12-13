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
        Point v0 = new Point(-1.2, 1);
        Point v1 = v0.plus(new Point(0.05, 0.05));
        Point v2 = v0.plus(new Point(0.05, 0.0025));
        expectedResult = 0.0;
        precision = 0.00001;
        assignBestGoodWorse(v0, v1, v2);

        int i = 0;
        while (i++ < 200 && resultIsNotAchieved()) {
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
        System.out.println(String.format("Best function: %1.5f", function(best)));
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
        return Functions.assymetricValley(point);
    }
}
