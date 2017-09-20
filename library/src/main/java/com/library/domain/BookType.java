package com.library.domain;

public enum BookType {
    USUAL(60, 10),
    RARE(21, 30),
    UNIQUE(7, 50)
    ;

    private final Integer daysBeforeFine;

    private final Integer finePerDay;

    BookType(Integer daysBeforeFine, Integer finePerDay) {
        this.daysBeforeFine = daysBeforeFine;
        this.finePerDay = finePerDay;
    }

    public Integer getDaysBeforeFine() {
        return daysBeforeFine;
    }

    public Integer getFinePerDay() {
        return finePerDay;
    }
}
