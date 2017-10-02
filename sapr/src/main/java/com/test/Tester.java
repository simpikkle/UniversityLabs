package com.test;

import com.test.utils.Logger;

import static com.test.utils.Reader.readRowAsFloatArray;
import static com.test.utils.Reader.readRowAsIntegerArray;
import static com.test.utils.Reader.readMatrixAsIntegerArray;
import static java.util.Arrays.stream;

public class Tester {

    private final static String RESOURCE_PATH = "src/main/resources/";

    public static void testCriteria() {
        Integer[][] matrix = readMatrixAsIntegerArray(RESOURCE_PATH + "matrix.txt");
        Criterion criterion = new Criterion(matrix);
        Logger.log("\nМинимаксный критерий");
        criterion.minMax();
        Logger.log("\n\n\n");
        Logger.log("\nКритерий Сэвиджа");
        criterion.savage();
        Logger.log("\n\n\n");
        Logger.log("\nКритерий Гурвица");
        criterion.hurwicz();
        Logger.log("\n\n\n");
    }

    public static void testBayes() {
        String fileName = RESOURCE_PATH + "amountsAndPossibilities.txt";
        Integer[] amount = readRowAsIntegerArray(fileName, 0);
        Float[] possibilities = readRowAsFloatArray(fileName, 1);
        Integer startPrice = 49000;
        Integer endPrice = 15000;
        Integer selfPrice = 25000;
        Integer[][] priceMatrix = new Integer[amount.length][amount.length];
        for (int i = 0; i < amount.length; i++) {
            int order = amount[i];
            for (int j = 0; j < amount.length; j++) {
                int sold = amount[j];
                if (sold <= order) {
                    priceMatrix[i][j] = sold * startPrice;
                    priceMatrix[i][j] += (order - sold) * endPrice;
                } else {
                    priceMatrix[i][j] = order * startPrice;
                }
                priceMatrix[i][j] -= order * selfPrice;
            }
        }
        Criterion criterion = new Criterion(priceMatrix);
        criterion.bayes(amount, possibilities);
    }
}
