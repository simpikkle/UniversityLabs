package optimization.domain;

public class Point {

    private double x[];

    private Point() {

    }

    public Point(double first, double second) {
        x = new double[2];
        x[0] = first;
        x[1] = second;
    }

    public Point(double x[]) {
        this.x = x;
    }

    public Point minus(Point p) {
        Point result = new Point();
        int size = this.x.length;
        result.x = new double[size];
        for (int i = 0; i < size; i++) {
            result.x[i] = this.x[i] - p.x[i];
        }
        return result;
    }

    public Point plus(Point p) {
        Point result = new Point();
        int size = this.x.length;
        result.x = new double[size];
        for (int i = 0; i < size; i++) {
            result.x[i] = this.x[i] + p.x[i];
        }
        return result;
    }

    public Point derive(double derivative) {
        Point result = new Point();
        int size = this.x.length;
        result.x = new double[size];
        for (int i = 0; i < size; i++) {
            result.x[i] = this.x[i] / derivative;
        }
        return result;
    }

    public Point multiply(double multiplier) {
        Point result = new Point();
        int size = this.x.length;
        result.x = new double[size];
        for (int i = 0; i < size; i++) {
            result.x[i] = this.x[i] * multiplier;
        }
        return result;
    }

    public double[] getAll() {
        return x;
    }

    public double getX() {
        return x[0];
    }

    public double getY() {
        return x[1];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (int i = 0; i < x.length; i++) {
            stringBuilder.append(String.format(" x%d=%1.8f ", i, x[i]));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
