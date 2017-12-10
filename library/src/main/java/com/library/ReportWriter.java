package com.library;

import com.library.domain.Journal;
import org.apache.logging.log4j.LogManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportWriter {
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(ReportWriter.class);

    //Delimiter used in CSV file
    private static final String COMA_SEPARATOR = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    public static void writeReport(String fileName, List<Journal> journals, String customInfo) {

        FileWriter fileWriter = null;
        String FILE_HEADER = "Дата возврата,Имя,Фамилия,Название книги";
        try {
            fileWriter = new FileWriter(fileName);
            if (customInfo != null && !customInfo.isEmpty()) fileWriter.append(customInfo).append(NEW_LINE_SEPARATOR);
            fileWriter.append(FILE_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (Journal journal : journals) {
                if (journal.getReturnDate() != null) {
                    fileWriter.append(journal.getReturnDate().toString());
                } else {
                    fileWriter.append("Не возвращенно");
                }
                fileWriter.append(COMA_SEPARATOR);
                fileWriter.append(journal.getClient().getFirstName());
                fileWriter.append(COMA_SEPARATOR);
                fileWriter.append(journal.getClient().getLastName());
                fileWriter.append(COMA_SEPARATOR);
                fileWriter.append(journal.getBook().getBookName());
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            LOGGER.info("CVS has been created");
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }}