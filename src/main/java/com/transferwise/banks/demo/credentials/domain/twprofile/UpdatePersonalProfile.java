package com.transferwise.banks.demo.credentials.domain.twprofile;

public class UpdatePersonalProfile {

    private Long id;
    private String type;
    private ProfileDetails details;

    public UpdatePersonalProfile() {
    }

    public UpdatePersonalProfile(Long id, String type, ProfileDetails details) {
        this.id = id;
        this.type = type;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public ProfileDetails getDetails() {
        return details;
    }
}
