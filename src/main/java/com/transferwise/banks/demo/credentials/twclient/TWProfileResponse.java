package com.transferwise.banks.demo.credentials.twclient;

class TWProfileResponse {

    private final Long id;
    private final String type;

    public TWProfileResponse(final Long id, final String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
