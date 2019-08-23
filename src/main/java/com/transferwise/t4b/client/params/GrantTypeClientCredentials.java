package com.transferwise.t4b.client.params;

public final class GrantTypeClientCredentials extends Parameter {

    public GrantTypeClientCredentials() {
        super("client_credentials");
    }

    @Override
    public String key() {
        return "grant_type";
    }
}
