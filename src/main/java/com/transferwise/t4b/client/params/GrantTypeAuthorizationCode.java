package com.transferwise.t4b.client.params;

public class GrantTypeAuthorizationCode implements Param {

    @Override
    public String key() {
        return "grant_type";
    }

    @Override
    public String value() {
        return "authorization_code";
    }
}
