package com.test;

import com.test.utils.Logger;

import java.util.List;

import static com.test.utils.Reader.readMatrix;

public class Tester {

    private final static String RESOURCE_PATH = "src/main/resources/";

    public static void testCriteria() {
        List<List<Integer>> matrix = readMatrix(RESOURCE_PATH + "matrix.txt");
        Criterion criterion = new Criterion(matrix);
        Logger.log("\n************");
        criterion.minMax();
        Logger.log("\n************");
        criterion.savage();
        Logger.log("\n************");
        criterion.hurwicz();
    }

    public static void testBL() {
        List<List<Integer>> matrix = readMatrix(RESOURCE_PATH + "mersedes.txt");
        Criterion criterion = new Criterion(matrix);
        criterion.bl(new float[]{0.2f, 0.15f, 0.25f, 0.2f, 0.2f},
                new float[]{100, 150, 200, 250, 300});
    }
}
