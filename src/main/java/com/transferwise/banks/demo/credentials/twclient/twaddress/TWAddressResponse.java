package com.transferwise.banks.demo.credentials.twclient.twaddress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.transferwise.banks.demo.credentials.domain.twaddress.TWAddress;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TWAddressResponse {

    private Long id;
    private Long profile;
    private TWAddress details;

    public TWAddressResponse() {}

    public TWAddressResponse(Long id, Long profile, TWAddress twAddress) {

        this.id = id;
        this.profile = profile;
        this.details = twAddress;
    }

    public Long getId() { return id; }
    public Long getProfile() { return profile; }
    public TWAddress getDetails() { return details; }
}
