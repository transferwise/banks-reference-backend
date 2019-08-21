package com.transferwise.t4b.client.params;

public class Email implements Param {

    private final String email;

    public Email(final String email) {
        this.email = email;
    }

    @Override
    public String key() {
        return "email";
    }

    @Override
    public String value() {
        return email;
    }
}
