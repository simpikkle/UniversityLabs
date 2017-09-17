package com.library.database;

import com.library.exception.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sql2o.Connection;

public class UserDao {

    private static final Logger logger = LogManager.getLogger(UserDao.class);

    public String getPasswordByUser(String username) throws DatabaseException {
        String query = "select password from user where username=:username";
        try(Connection connection = DbConnection.getConnection()) {
            return connection.createQuery(query)
                    .addParameter("username", username)
                    .executeScalar(String.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e.getMessage(), e);
        }
    }
}
