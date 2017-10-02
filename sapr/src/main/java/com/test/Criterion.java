package com.test;

import com.test.utils.Logger;

import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.stream;

public class Criterion {

    private int numberOfRows;
    private int numberOfColumns;
    private Integer[][] matrix;

    public Criterion(Integer[][] matrix) {
        this.matrix = matrix;
        this.numberOfRows = matrix.length;
        this.numberOfColumns = matrix[0].length;
    }

    public Integer minMax() {
        Integer[] columnMin = new Integer[numberOfColumns];
        Logger.log("\nИсходная матрица: \n");
        Logger.log(matrix);
        Logger.log("Минимумы по столбцам: \n");
        Integer[] rowWithMinColumn = new Integer[numberOfColumns];
        int k = 0;
        for (int j = 0; j < numberOfColumns; j++) {
            columnMin[j] = matrix[0][j];
            for (int i = 0; i < numberOfRows; i++) {
                if (matrix[i][j] < columnMin[j]) {
                    columnMin[j] = matrix[i][j];
                    rowWithMinColumn[k++] = i+1;
                }
            }
            Logger.log("\t" + columnMin[j]);
        }
        Integer maxRow = columnMin[0];
        int rowIndex = 0;
        for (int i = 0; i < numberOfColumns; i++) {
            if (columnMin[i] > maxRow) {
                maxRow = columnMin[i];
                rowIndex = rowWithMinColumn[i];
            }
        }
        Logger.log("\nРезультат минимаксного критерия (максимальный минимум по столбцам): " + maxRow);
        Logger.log("\nСледует выбрать стратегию " + rowIndex);
        return rowIndex;
    }

    public Integer savage() {
        Logger.log("\nИсходная матрица: \n");
        Logger.log(matrix);
        Integer[] columnMax = new Integer[numberOfColumns];
        Integer[][] riskMatrix = new Integer[numberOfRows][numberOfColumns];
        Logger.log("Максимумы по столбцам: \n");
        for (int j = 0; j < numberOfColumns; j++) {
            columnMax[j] = matrix[0][j];
            for (int i = 0; i < numberOfRows; i++) {
                if (matrix[i][j] > columnMax[j]) {
                    columnMax[j] = matrix[i][j];
                }
            }
            Logger.log("\t" + columnMax[j]);

            for (int i = 0; i < numberOfRows; i++) {
                riskMatrix[i][j] = columnMax[j] - matrix[i][j];
            }
        }
        Logger.log("\nМатрица рисков: \n");
        Logger.log(riskMatrix);
        Integer[] maxRiskPerRow = new Integer[numberOfRows];
        Logger.log("Масимальный рик по строкам: \n");
        for (int i = 0; i < numberOfRows; i++) {
            maxRiskPerRow[i] = stream(riskMatrix[i]).max(Comparator.naturalOrder()).get();
            Logger.log(maxRiskPerRow[i] + "\n");
        }

        Integer minRisk = maxRiskPerRow[0];
        int rowIndex = 0;
        for (int i = 0; i < numberOfRows; i++) {
            if (maxRiskPerRow[i] < minRisk) {
                minRisk = maxRiskPerRow[i];
                rowIndex = i+1;
            }
        }
        Logger.log("Результат критерия Сэвиджа (минимальный риск из максимальных по строкам): " + minRisk);
        Logger.log("\nСледует выбрать стратегию " + rowIndex);
        return rowIndex;
    }

    public Integer hurwicz() {
        Logger.log("\nИсходная матрица: \n");
        Logger.log(matrix);
        float c = 0.25f;
        Logger.log("Коэффициент C = " + c + " \n");
        Logger.log("Результат по строкам: \n");
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
            }
            resultPerRow[i] = c * min + (1 - c) * max;
            Logger.log("Min = " + min + ", max = " + max + ", C*MIN + (1-C)*MAX = " + resultPerRow[i] + "\n");
        }
        Float maxRow = resultPerRow[0];
        int rowIndex = 0;
        for (int i = 0; i < numberOfRows; i++) {
            if (resultPerRow[i] > maxRow) {
                maxRow = resultPerRow[i];
                rowIndex = i+1;
            }
        }
        Logger.log("\nРезультат критерия Гурвица (максимальный по строкам): " + maxRow);
        Logger.log("\nСледует выбрать стратегию " + rowIndex);
        return rowIndex;
    }

    public float bayes(Integer[] amount, Float[] probabilities) {
        Logger.log("\nИсходная матрица: \n");
        Logger.log(matrix);
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
        Logger.log("\nРезультат критерия Байеса-Лапласа: " + maxSum);
        Logger.log("\nСледует выбрать стратегию " + amount[maxRowIndex]);
        return amount[maxRowIndex];
    }
}
