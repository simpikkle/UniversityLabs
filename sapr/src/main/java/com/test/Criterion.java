package com.test;

import com.test.utils.Logger;

import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.stream;

public class Criterion {

    private List<List<Integer>> matrixList;
    private int numberOfRows;
    private int numberOfColumns;
    private Integer[][] matrix;

    public Criterion(List<List<Integer>> matrixList) {
        this.matrixList = matrixList;
        fillMatrix();
    }

    private void fillMatrix() {
        numberOfRows = matrixList.size();
        numberOfColumns = matrixList.get(0).size();
        matrix = new Integer[numberOfRows][numberOfColumns];
        int i = 0;
        for (List<Integer> row : matrixList) {
            for (int j = 0; j < numberOfColumns; j++) {
                matrix[i][j] = row.get(j);
            }
            i++;
        }
        Logger.log("Initial matrix: \n");
        Logger.log(matrix);
    }

    public Integer minMax() {
        Logger.log("\nMinMax criterion");
        Integer[] columnMin = new Integer[numberOfColumns];
        Logger.log("\nColumns' minimums: \n");
        for (int j = 0; j < numberOfColumns; j++) {
            columnMin[j] = matrix[0][j];
            for (int i = 0; i < numberOfRows; i++) {
                if (matrix[i][j] < columnMin[j]) {
                    columnMin[j] = matrix[i][j];
                }
            }
            Logger.log("\t" + columnMin[j]);
        }
        Integer result = stream(columnMin).max(Comparator.naturalOrder()).get();
        Logger.log("\nMinMax criterion result (max of min) is " + result);
        return result;
    }

    public Integer savage() {

        Logger.log("\nSavage criterion");
        Integer[] columnMax = new Integer[numberOfColumns];
        Integer[][] riskMatrix = new Integer[numberOfRows][numberOfColumns];
        Logger.log("\nColumns' maximum: \n");
        for (int j = 0; j < numberOfColumns; j++) {
            columnMax[j] = matrix[0][j];
            // Find max in column
            for (int i = 0; i < numberOfRows; i++) {
                if (matrix[i][j] > columnMax[j]) {
                    columnMax[j] = matrix[i][j];
                }
            }
            Logger.log("\t" + columnMax[j]);
            // Fill risk matrix
            for (int i = 0; i < numberOfRows; i++) {
                riskMatrix[i][j] = columnMax[j] - matrix[i][j];
            }
        }
        Integer[] maxRiskPerRow = new Integer[numberOfRows];
        for (int i = 0; i < numberOfRows; i++) {
            maxRiskPerRow[i] = stream(riskMatrix[i]).max(Comparator.naturalOrder()).get();
            Logger.log("\n Max risk per row: " + maxRiskPerRow[i]);
        }

        Integer result = stream(maxRiskPerRow).min(Comparator.naturalOrder()).get();
        Logger.log("\nSavage criterion result is " + result);
        return result;
    }

    public Float hurwicz() {
        Logger.log("\nHurwicz criterion");
        float c = 0.25f;
        Float[] resultPerRow = new Float[numberOfRows];
        for (int i = 0; i < numberOfRows; i++) {
            int min = matrix[i][0];
            int max = matrix[i][0];
            for (int j = 0; j < numberOfColumns; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                }
                resultPerRow[i] = c * min + (1 - c) * max;
            }
        }
        Float result = stream(resultPerRow).max(Comparator.naturalOrder()).get();
        Logger.log("\nHurwicz criterion result is " + result);
        return result;
    }

    public float bl(float[] probabilities, float[] amount) {
        Logger.log("\nB-L criterion");
        Float[] rowSum = new Float[numberOfRows];
        for (int i = 0; i < numberOfRows; i++) {
            rowSum[i] = 0f;
            for (int j = 0; j < numberOfColumns; j++) {
                rowSum[i] += matrix[i][j] * probabilities[j];
            }
        }
        float maxSum = 0f;
        int maxRowIndex = 0;
        for (int i = 0; i < numberOfRows; i++) {
            if (maxSum < rowSum[i]) {
                maxSum = rowSum[i];
                maxRowIndex = i;
            }
        }
        Float result = stream(rowSum).max(Comparator.naturalOrder()).get();
        Logger.log("\nB-L result is " + result);
        Logger.log("\nAmount to order is " + amount[maxRowIndex]);
        return result;
    }
}
