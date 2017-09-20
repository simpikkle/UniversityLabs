package com.library.database;

import org.junit.Test;

public class DaoTest {

    private ClientDao clientDao = new ClientDao();
    private BookDao bookDao = new BookDao();
    private JournalDao journalDao = new JournalDao();

    @Test
    public void cleanDB() {
        journalDao.deleteAll();
        clientDao.deleteAll();
        bookDao.deleteAll();
    }
}
