package optimization;

import java.util.*;

public class Pareto implements Lab {

    private static List<Flat> flats = new ArrayList<>();
    private static int numberOfFactors = 6;

    @Override
    public void run() {
        System.out.println("Исходные данные: ");
        initializeDefaultItems();
        printList(flats);
        runPareto();
        printList(flats);

        System.out.println("Индивидуальное задание: ");
        initializeUniqueItems();
        printList(flats);
        runPareto();
        printList(flats);
    }

    private void runPareto() {
        Set<Flat> toRemove = new HashSet<>();
        for (Flat outerFlat : flats) {
            for (Flat innerFlat : flats) {
                if (isWorse(outerFlat, innerFlat)) {
                    toRemove.add(outerFlat);
                }
            }
        }
        flats.removeAll(toRemove);
    }

    private static boolean isWorse(Flat flat, Flat anotherFlat) {
        int worse = 0;
        int equal = 0;
        int better = 0;

        for (int j = 0; j < numberOfFactors; j++) {
            if (flat.factors[j] < anotherFlat.factors[j]) {
                worse++;
            } else if (flat.factors[j] == anotherFlat.factors[j]) {
                equal++;
            } else
                better++;
        }
        if (worse > 0 && equal >= 0 && better == 0) {
            System.out.println("УДАЛЕНИЕ " + flat.name + " и " + anotherFlat.name
                    + ": Факторы, которые хуже = " + worse + ", равны = " + equal + ", лучше = " + better);
            return true;
        }
        return false;
    }

    private static void printList(List<Flat> list) {
        for (Flat flat : list) {
            System.out.println(flat);
        }
        System.out.println();
    }

    private static void initializeDefaultItems() {
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

    }

    private static void initializeUniqueItems() {
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
    }

    private static double[] randomDoubleArray() {
        double[] randomArray = new double[numberOfFactors];
        for (int i = 0; i < numberOfFactors; i++) {
            randomArray[i] = new Random().nextInt(9) + 1;
        }
        return randomArray;
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
