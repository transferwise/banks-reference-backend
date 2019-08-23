package com.transferwise.t4b.client.params;

public final class GrantTypeAuthorizationCode extends Parameter {

    public GrantTypeAuthorizationCode() {
        super("authorization_code");
    }

    @Override
    public String key() {
        return "grant_type";
    }
}
