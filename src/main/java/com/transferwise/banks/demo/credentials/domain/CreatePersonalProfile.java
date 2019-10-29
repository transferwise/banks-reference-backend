package com.transferwise.banks.demo.credentials.domain;

public class CreatePersonalProfile {

    private final String type;
    private final CreatePersonalProfileDetails details;

    public CreatePersonalProfile(String type, CreatePersonalProfileDetails details) {
        this.type = type;
        this.details = details;
    }
}
