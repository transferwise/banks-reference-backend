package com.transferwise.banks.demo.client.params;

import static java.util.UUID.randomUUID;

public final class RegistrationCode extends Parameter {

    public RegistrationCode() {
        super(randomUUID().toString());
    }

    public RegistrationCode(final String registrationCode) {
        super(registrationCode);
    }

    @Override
    public String key() {
        return "registration_code";
    }

    public V1RegistrationCode v1() {
        return new V1RegistrationCode(value());
    }
}
