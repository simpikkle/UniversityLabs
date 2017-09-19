package com.library.domain;

public enum BookType {
    USUAL(60, 10),
    RARE(21, 50),
    UNIQUE(7, 30)
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
