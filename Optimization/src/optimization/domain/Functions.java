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

    public static double asymmetricValley(Point point) {
        double x = point.getX();
        double y = point.getY();
        return pow((x - 3) / 100, 2) - (y - x) + exp(20 * (y - x));
    }

    public static double pauell(Point point) {
        double points[] = point.getAll();
        double x1 = points[0];
        double x2 = points[1];
        double x3 = points[2];
        double x4 = points[3];
        return pow((x1 + 10 * pow(x2, 2)), 2) + 5 * pow((x3 - x4), 2) + pow((x2 - 2 * x3), 4) + 10 * pow((x1 - x4), 4);
    }

    public static double experimental(Point point) {
        double points[] = point.getAll();
        double x1 = points[0];
        double x2 = points[1];
        double x3 = points[2];
        double x4 = points[3];

        double a[] = { 0.0, 0.000428, 0.001, 0.00161, 0.00209, 0.00348, 0.00525 };
        double b[] = { 7.391, 11.18, 16.44, 16.20, 22.2, 24.02, 31.32 };
        double sum = 0.0;
        for (int i = 0; i < 7; i++) {
            sum += pow(((pow(x1, 2) + pow(x2, 2) * a[i]
                    + pow(x3, 2) * pow(a[i], 2)) / (1.0 + pow(x4, 2) * a[i]) - b[i]) / b[i], 2);
        }
        return sum * pow(10, 4);
    }
}
