package com.transferwise.banks.demo.credentials.domain.twprofile;

public class CreatePersonalProfile {

    private String type;
    private ProfileDetails details;

    public CreatePersonalProfile() {
    }

    public CreatePersonalProfile(String type, ProfileDetails details) {
        this.type = type;
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public ProfileDetails getDetails() {
        return details;
    }
}
