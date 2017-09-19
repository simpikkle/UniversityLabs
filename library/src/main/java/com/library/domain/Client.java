package com.library.domain;

public class Client {
    private String firstName;
    private String lastName;
    private String passportNumber;

    public Client withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Client withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Client withPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }
}
