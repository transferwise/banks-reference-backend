package com.transferwise.t4b.client.params;

public final class Email extends Parameter {

    public Email(final String email) {
        super(email);
    }

    @Override
    public String key() {
        return "email";
    }
}
