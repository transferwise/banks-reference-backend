package com.transferwise.banks.demo.credentials.twclient;

class TWProfileResponse {

    private Long id;
    private String type;

    public TWProfileResponse() {
    }

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
