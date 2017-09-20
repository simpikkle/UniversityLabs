package com.library.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.library.Utils.defaultDateFormatter;

public class JournalRecord extends BaseObject {

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

    @Override
    public Map<String, String> getParameterMapping() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("book_id", String.valueOf(this.getBook().getId()));
        parameters.put("client_id", String.valueOf(this.getClient().getId()));
        parameters.put("date_start", this.getStartDate().format(defaultDateFormatter()));
        parameters.put("date_end", this.getEndDate().format(defaultDateFormatter()));
        parameters.put("date_return", this.getReturnDate().format(defaultDateFormatter()));
        return parameters;
    }
}
