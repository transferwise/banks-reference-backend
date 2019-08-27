package com.transferwise.t4b.client.params;

public final class GrantTypeRegistrationCode extends Parameter {

    public GrantTypeRegistrationCode() {
        super("registration_code");
    }

    @Override
    public String key() {
        return "grant_type";
    }
}
