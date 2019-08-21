package com.transferwise.t4b.client.params;

public final class GrantTypeClientCredentials implements Param {

    @Override
    public String key() {
        return "grant_type";
    }

    @Override
    public String value() {
        return "client_credentials";
    }
}
