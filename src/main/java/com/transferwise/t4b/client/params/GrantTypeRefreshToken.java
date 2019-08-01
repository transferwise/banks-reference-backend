package com.transferwise.t4b.client.params;

public class GrantTypeRefreshToken implements Param {

    @Override
    public String key() {
        return "grant_type";
    }

    @Override
    public String value() {
        return "refresh_token";
    }
}
