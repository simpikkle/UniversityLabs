package com.library;

import com.library.database.DbConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Utils {
    private static final Logger logger = LogManager.getLogger();

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;

    public static DateTimeFormatter defaultDateFormatter() {
        return DATE_FORMATTER;
    }

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
