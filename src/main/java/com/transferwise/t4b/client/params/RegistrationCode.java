package com.transferwise.t4b.client.params;

import java.util.UUID;

public class RegistrationCode implements Param {

    private final UUID code;

    public RegistrationCode() {
        code = UUID.randomUUID();
    }

    @Override
    public String key() {
        return "registrationCode";
    }

    @Override
    public String value() {
        return code.toString();
    }
}
