package com.transferwise.banks.demo.credentials.domain;

public final class CreatePersonalProfileDetails {

    private final String firstName;
    private final String lastName;
    private final String dateOfBirth;
    private final String phoneNumber;

    public CreatePersonalProfileDetails(String firstName, String lastName, String dateOfBirth, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }
}
