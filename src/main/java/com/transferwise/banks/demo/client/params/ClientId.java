package com.transferwise.banks.demo.client.params;

public final class ClientId extends Parameter {

    public ClientId(final String clientId) {
        super(clientId);
    }

    @Override
    public String key() {
        return "client_id";
    }
}
