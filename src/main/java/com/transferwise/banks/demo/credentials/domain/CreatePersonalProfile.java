package com.transferwise.banks.demo.credentials.domain;

public class CreatePersonalProfile {

    private String type;
    private CreatePersonalProfileDetails details;

    public CreatePersonalProfile() {
    }

    public CreatePersonalProfile(String type, CreatePersonalProfileDetails details) {
        this.type = type;
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public CreatePersonalProfileDetails getDetails() {
        return details;
    }
}
