package com.test.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.test.utils.Logger.log;
import static java.util.Arrays.stream;

public class Reader {

    public static List<List<String>> readMatrix(String fileName)  {
        List<List<String>> resultList = new ArrayList<>();
        try {
            List<String> initialList = Arrays.asList(readToString(fileName).split("\\n"));
            for (String row : initialList) {
                List<String> rowList = new ArrayList<>();
                stream(row.split(" +")).forEach(rowList::add);
                resultList.add(rowList);
            }
        } catch (IOException e) {
            log("Error: " + e);
        }
        return resultList;
    }

    public static Integer[][] readMatrixAsIntegerArray(String fileName) {
        List<List<String>> matrixList = readMatrix(fileName);
        int numberOfRows = matrixList.size();
        int numberOfColumns = matrixList.get(0).size();
        Integer[][] result = new Integer[numberOfRows][numberOfColumns];
        int i = 0;
        for (List<String> row : matrixList) {
            for (int j = 0; j < numberOfColumns; j++) {
                result[i][j] = Integer.parseInt(row.get(j));
            }
            i++;
        }
        return result;
    }

    public static Integer[] readRowAsIntegerArray(String fileName, int numberOfRow) {
        List<List<String>> matrixList = readMatrix(fileName);
        int numberOfColumns = matrixList.get(numberOfRow).size();
        Integer[] result = new Integer[numberOfColumns];
        for (int i = 0; i < numberOfColumns; i++) {
            result[i] = Integer.parseInt(matrixList.get(numberOfRow).get(i));
        }
        return result;
    }

    public static Float[] readRowAsFloatArray(String fileName, int numberOfRow) {
        List<List<String>> matrixList = readMatrix(fileName);
        int numberOfColumns = matrixList.get(numberOfRow).size();
        Float[] result = new Float[numberOfColumns];
        for (int i = 0; i < numberOfColumns; i++) {
            result[i] = Float.parseFloat(matrixList.get(numberOfRow).get(i));
        }
        return result;
    }

    private static String readToString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}
