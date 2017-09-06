package com.test;

import com.test.utils.Logger;

import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.stream;

public class Criterion {

    private List<List<Integer>> matrixList;
    private int rowSize;
    private int columnSize;
    private Integer[][] matrix;

    public Criterion(List<List<Integer>> matrixList) {
        this.matrixList = matrixList;
        fillMatrix();
    }

    private void fillMatrix() {
        rowSize = matrixList.size();
        columnSize = matrixList.get(0).size();
        matrix = new Integer[rowSize][columnSize];
        int i = 0;
        for (List<Integer> row: matrixList){
            for (int j = 0; j < columnSize; j++) {
                matrix[i][j] = row.get(j);
            }
            i++;
        }
        Logger.log("Initial matrix: \n");
        Logger.log(matrix);
    }

    public Integer minMax() {
        Logger.log("\nMinMax criterion");
        Integer[] columnMin = new Integer[columnSize];
        Logger.log("\nColumns' minimums: \n");
        for (int j = 0; j < columnSize; j++) {
            columnMin[j] = matrix[0][j];
            for (int i = 0; i < rowSize; i++) {
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
        Integer[] columnMax = new Integer[columnSize];
        Integer[][] riskMatrix = new Integer[rowSize][columnSize];
        Logger.log("\nColumns' maximum: \n");
        for (int j = 0; j < columnSize; j++) {
            columnMax[j] = matrix[0][j];
            // Find max in column
            for (int i = 0; i < rowSize; i++) {
                if (matrix[i][j] > columnMax[j]) {
                    columnMax[j] = matrix[i][j];
                }
            }
            Logger.log("\t" + columnMax[j]);
            // Fill risk matrix
            for (int i = 0; i < rowSize; i++) {
                riskMatrix[i][j] = columnMax[j] - matrix[i][j];
            }
        }
        Integer[] maxRiskPerRow = new Integer[rowSize];
        for (int i = 0; i < rowSize; i++) {
            maxRiskPerRow[i] = stream(riskMatrix[i]).max(Comparator.naturalOrder()).get();
            Logger.log("\n Max risk per row: " + maxRiskPerRow[i]);
        }

        Integer result = stream(maxRiskPerRow).min(Comparator.naturalOrder()).get();
        Logger.log("\nSavage criterion result is " + result);
        return result;
    }

    public Integer hurwicz() {
        Logger.log("\nHurwicz criterion");
        Integer[] columnMin = new Integer[columnSize];
        Logger.log("\nColumns' minimums: \n");
        for (int j = 0; j < columnSize; j++) {
            columnMin[j] = matrix[0][j];
            for (int i = 0; i < rowSize; i++) {
                if (matrix[i][j] < columnMin[j]) {
                    columnMin[j] = matrix[i][j];
                }
            }
            Logger.log("\t" + columnMin[j]);
        }
        Integer result = stream(columnMin).max(Comparator.naturalOrder()).get();
        Logger.log("\nHurwicz criterion result (max of min) is " + result);
        return result;
    }
}
