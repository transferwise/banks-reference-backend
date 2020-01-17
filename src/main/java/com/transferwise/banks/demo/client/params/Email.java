package com.transferwise.banks.demo.client.params;

public final class Email extends Parameter {

    public Email(final String email) {
        super(email);
    }

    @Override
    public String key() {
        return "email";
    }
}
