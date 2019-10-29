package com.transferwise.banks.demo.credentials.twclient;

class TWSignUpRequest {

    private String email;
    private String registrationCode;

    TWSignUpRequest(String email, String registrationCode) {
        this.email = email;
        this.registrationCode = registrationCode;
    }

    public String getEmail() {
        return email;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }
}
