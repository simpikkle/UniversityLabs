package com.library.domain;

import java.time.LocalDate;

public class JournalRecord {

    private Client client;
    private Book book;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate returnDate;

    public JournalRecord withClient(Client client) {
        this.client = client;
        return this;
    }

    public JournalRecord withBook(Book book) {
        this.book = book;
        return this;
    }

    public JournalRecord withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public JournalRecord withEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public JournalRecord withReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public Client getClient() {
        return client;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}
