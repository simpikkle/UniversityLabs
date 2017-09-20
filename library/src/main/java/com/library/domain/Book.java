package com.library.domain;

import java.util.HashMap;
import java.util.Map;

public class Book extends BaseObject {

    private String bookName;
    private Integer amount;
    private BookType bookType;

    public Book withBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public Book withAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public Book withBookType(BookType bookType) {
        this.bookType = bookType;
        return this;
    }

    public String getBookName() {
        return bookName;
    }

    public Integer getAmount() {
        return amount;
    }

    public BookType getBookType() {
        return bookType;
    }

    @Override
    public Map<String, String> getParameterMapping() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", this.getBookName());
        parameters.put("amount", String.valueOf(this.getAmount()));
        parameters.put("book_type_id", String.valueOf(this.getBookType().ordinal()));
        return parameters;
    }
}
