package com.transferwise.banks.demo.credentials.domain.twaddress;


public class CreateAddress {

    private Long profile;
    private TWAddress details;

    public CreateAddress() {}

    public CreateAddress(Long profile, TWAddress details) {

        this.profile = profile;
        this.details = details;
    }

    public Long getProfile() { return profile; }
    public TWAddress getDetails() { return details; }
}
