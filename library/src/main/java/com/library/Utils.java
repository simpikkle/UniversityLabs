package com.library;

import com.library.database.BookDao;
import com.library.database.ClientDao;
import com.library.database.DbConnection;
import com.library.database.JournalDao;
import com.library.domain.Journal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Utils {
    private static final Logger logger = LogManager.getLogger();

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            InputStream stream = DbConnection.class.getClassLoader().getResourceAsStream("environment.properties");
            properties.load(stream);
        } catch (IOException e) {
            logger.error(e);
        }
        return properties;
    }
}
