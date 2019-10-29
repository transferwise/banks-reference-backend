package com.transferwise.banks.demo.credentials.domain;

public class TWProfile {

    private final Long twProfileId;
    private final Long customerId;
    private final String type;

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
