package optimization.domain;

import static java.lang.Math.exp;
import static java.lang.Math.pow;

public class Functions {


    public static double square(Point point) {
        double x = point.getX();
        double y = point.getY();
        return (pow(x - y, 2) + pow(x + y - 10, 2)) / 9;
    }

    public static double test(Point point) {
        double x = point.getX();
        double y = point.getY();
        return (pow(x, 2) + x * y + pow(x, 2)) - 6 * x - 9 * y;
    }

    public static double rosenbrok(Point point) {
        double x = point.getX();
        double y = point.getY();
        return 100 * pow((pow(x, 2) - y), 2) + pow(1 - x, 2);
    }

    public static double assymetricValley(Point point) {
        double x = point.getX();
        double y = point.getY();
        return pow((x - 3) / 100, 2) - (y - x) + exp(20 * (y - x));
    }
}
