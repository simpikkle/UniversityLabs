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

    public static List<List<Integer>> readMatrix(String fileName)  {
        List<List<Integer>> resultList = new ArrayList<>();
        try {
            List<String> initialList = Arrays.asList(readToString(fileName).split("\\n"));
            for (String row : initialList) {
                List<Integer> integerList = new ArrayList<>();
                stream(row.split("\t")).forEach(stringValue ->
                        integerList.add(Integer.parseInt(stringValue)));
                resultList.add(integerList);
            }
        } catch (IOException e) {
            log("Error: " + e);
        }
        return resultList;
    }

    private static String readToString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}
