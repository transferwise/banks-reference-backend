package com.transferwise.banks.demo.credentials.twclient.twprofile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class TWProfileResponse {

    private Long id;
    private String type;
    private TWProfileDetails details;

    public TWProfileResponse() {
    }

    public TWProfileResponse(Long id, String type, TWProfileDetails details) {
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

    public TWProfileDetails getDetails() {
        return details;
    }
}
