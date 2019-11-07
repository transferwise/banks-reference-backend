package com.transferwise.banks.demo.credentials.domain;

public class TWProfile {

    private Long twProfileId;
    private Long customerId;
    private String type;

    public TWProfile() {
    }

    public TWProfile(Long twProfileId, Long customerId, String type) {
        this.twProfileId = twProfileId;
        this.customerId = customerId;
        this.type = type;
    }

    public Long getTwProfileId() {
        return twProfileId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getType() {
        return type;
    }
}
