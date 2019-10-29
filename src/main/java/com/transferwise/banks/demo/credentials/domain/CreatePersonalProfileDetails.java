package com.transferwise.banks.demo.credentials.domain;

public final class CreatePersonalProfileDetails {

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String phoneNumber;

    public CreatePersonalProfileDetails() {
    }

    public CreatePersonalProfileDetails(String firstName, String lastName, String dateOfBirth, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
