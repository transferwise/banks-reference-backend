package com.transferwise.banks.demo.client.params;

public final class GrantTypeRefreshToken extends Parameter {

    public GrantTypeRefreshToken() {
        super("refresh_token");
    }

    @Override
    public String key() {
        return "grant_type";
    }
}
