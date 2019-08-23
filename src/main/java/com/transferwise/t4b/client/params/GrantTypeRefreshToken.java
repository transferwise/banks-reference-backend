package com.transferwise.t4b.client.params;

public final class GrantTypeRefreshToken extends Parameter {

    public GrantTypeRefreshToken() {
        super("refresh_token");
    }

    @Override
    public String key() {
        return "grant_type";
    }
}
