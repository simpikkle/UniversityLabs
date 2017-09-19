package com.library.domain;

public class Book {

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
}
