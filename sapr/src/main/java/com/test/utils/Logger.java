package com.test.utils;

public class Logger {
    public static void log(String text) {
        System.out.print(text);
    }

    public static void log(Number[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                log("\t" + matrix[i][j]);
            }
            log("\n");
        }
    }
}
