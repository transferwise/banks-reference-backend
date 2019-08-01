package com.transferwise.t4b.client.params;

public class ClientId implements Param {

    private final String clientId;

    public ClientId(final String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String key() {
        return "client_id";
    }

    @Override
    public String value() {
        return clientId;
    }
}
