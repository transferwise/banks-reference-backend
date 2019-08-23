package com.transferwise.t4b.client.params;

import static java.util.UUID.randomUUID;

public final class RegistrationCode extends Parameter {

    public RegistrationCode() {
        super(randomUUID().toString());
    }

    @Override
    public String key() {
        return "registrationCode";
    }
}
