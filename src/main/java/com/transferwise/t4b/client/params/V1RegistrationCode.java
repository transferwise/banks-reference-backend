package com.transferwise.t4b.client.params;

public final class V1RegistrationCode extends Parameter {

    V1RegistrationCode(final String registrationCode) {
        super(registrationCode);
    }

    @Override
    public String key() {
        return "registrationCode";
    }
}
