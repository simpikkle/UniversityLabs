package optimization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.stream;

public class GeneralCriteria implements Lab {

    private List<Flat> flats = new ArrayList<>();
    private double[] alfa = {0.23, 0.05, 0.15, 0.09, 0.47, 0.01};

    private static double[] randomDoubleArray() {
        double[] randomArray = new double[6];
        for (int i = 0; i < 6; i++) {
            randomArray[i] = new Random().nextInt(9) + 1;
        }
        return randomArray;
    }

    private static void printList(List<Flat> list) {
        for (Flat flat : list) {
            System.out.println(flat);
        }
        System.out.println();
    }

    @Override
    public void run() {
        System.out.println("Исходные данные задачи: ");
        initializeDefaultItems();
        if (stream(alfa).sum() != 1.0) {
            throw new IllegalArgumentException("Сумма массива альфа должна равняться единице!");
        }
        printList(flats);
        addictive();
        multiplicative();
        minFolding();
        maxFolding();

        System.out.println("Индивидуальные значения: ");
        initializeUniqueItems();
        if (stream(alfa).sum() != 1.0) {
            throw new IllegalArgumentException("Сумма массива альфа должна равняться единице!");
        }
        printList(flats);
        addictive();
        multiplicative();
        minFolding();
        maxFolding();

    }

    private void addictive() {
        System.out.println("\nАддитивная свертка");
        double max = 0.0;
        Flat betterFlat = flats.get(0);
        for (Flat flat : flats) {
            double flatSum = 0;
            for (int i = 0; i < alfa.length; i++) {
                flatSum += alfa[i] * flat.getFactors()[i];
            }
            System.out.println(String.format("%s: %1.2f", flat.getName(), flatSum));
            if (flatSum > max) {
                max = flatSum;
                betterFlat = flat;
            }
        }
        System.out.println(String.format("Вариант квартиры №%s лучший по аддитивной свертке = %1.2f", betterFlat.getName(), max));
    }

    private void multiplicative() {
        System.out.println("\nМультипликативная свертка");
        double max = 0.0;
        Flat betterFlat = flats.get(0);
        for (Flat flat : flats) {
            double multiplication = 1;
            for (int i = 0; i < alfa.length; i++) {
                multiplication *= Math.pow(flat.getFactors()[i], alfa[i]);
            }
            System.out.println(String.format("%s: %1.2f", flat.getName(), multiplication));
            if (multiplication > max) {
                max = multiplication;
                betterFlat = flat;
            }
        }
        System.out.println(String.format("Вариант квартиры №%s лучший по мультипликативной свертке = %1.2f", betterFlat.getName(), max));
    }

    private void minFolding() {
        System.out.println("\nСвертка по минимуму");
        double max = 0;
        Flat betterFlat = flats.get(0);
        for (Flat flat : flats) {
            double totalMin = -1;
            double min[] = new double[alfa.length];
            for (int i = 0; i < alfa.length; i++) {
                min[i] = flat.getFactors()[i] / alfa[i];
                if (min[i] < totalMin || totalMin == -1) {
                    totalMin = min[i];
                }
            }
            if (totalMin > max) {
                max = totalMin;
                betterFlat = flat;
            }
            System.out.println(String.format("%s: %1.2f", flat.getName(), totalMin));
        }
        System.out.println(String.format("Вариант квартиры №%s лучший по свертке по минимуму = %1.2f", betterFlat.getName(), max));
    }

    private void maxFolding() {
        System.out.println("\nСвертка по максимуму");
        double max = 0;
        Flat betterFlat = flats.get(0);
        for (Flat flat : flats) {
            double totalMax = -1;
            double rowMax[] = new double[alfa.length];
            for (int i = 0; i < alfa.length; i++) {
                rowMax[i] = flat.getFactors()[i] * alfa[i];
                if (rowMax[i] > totalMax || totalMax == -1) {
                    totalMax = rowMax[i];
                }
            }
            if (totalMax > max) {
                max = totalMax;
                betterFlat = flat;
            }
            System.out.println(String.format("%s: %1.2f", flat.getName(), totalMax));
        }
        System.out.println(String.format("Вариант квартиры №%s лучший по свертке по максимуму = %1.2f", betterFlat.getName(), max));

    }

    private void initializeDefaultItems() {
        flats = new ArrayList<>();
        flats.add(new Flat("U1", new double[]{10, 2, 3, 2, 2, 4}));
        flats.add(new Flat("U2", new double[]{4, 7, 3, 4, 3, 1}));
        flats.add(new Flat("U3", new double[]{6, 8, 3, 5, 4, 2}));
        flats.add(new Flat("U4", new double[]{9, 2, 3, 2, 1, 3}));
        flats.add(new Flat("U5", new double[]{5, 1, 0.1, 5, 2, 2}));
        flats.add(new Flat("U6", new double[]{3, 8, 0.1, 2, 4, 1}));
        flats.add(new Flat("U7", new double[]{3, 5, 4, 2, 7, 7}));
        flats.add(new Flat("U8", new double[]{3, 6, 3, 4, 4, 1}));
        flats.add(new Flat("U9", new double[]{2, 5, 3, 1, 5, 7}));
        alfa = new double[]{0.23, 0.05, 0.15, 0.09, 0.47, 0.01};
    }

    private void initializeUniqueItems() {
        flats = new ArrayList<>();
        flats.add(new Flat("U1", randomDoubleArray()));
        flats.add(new Flat("U2", randomDoubleArray()));
        flats.add(new Flat("U3", randomDoubleArray()));
        flats.add(new Flat("U4", randomDoubleArray()));
        flats.add(new Flat("U5", randomDoubleArray()));
        flats.add(new Flat("U6", randomDoubleArray()));
        flats.add(new Flat("U7", randomDoubleArray()));
        flats.add(new Flat("U8", randomDoubleArray()));
        flats.add(new Flat("U9", randomDoubleArray()));
        alfa = new double[]{0.2, 0.15, 0.05, 0.07, 0.23, 0.3};
    }

    private static class Flat {

        private String name;
        private double[] factors = new double[6];

        Flat(String name, double[] factors) {
            this.name = name;
            this.factors = factors;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double[] getFactors() {
            return factors;
        }

        public void setFactors(double[] factors) {
            this.factors = factors;
        }

        public String toString() {
            StringBuilder tmp = new StringBuilder();
            for (double factor : factors) {
                tmp.append(String.format("%3.0f", factor));
            }
            return name + tmp.toString();
        }
    }
}
