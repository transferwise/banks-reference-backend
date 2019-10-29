package com.transferwise.banks.demo.credentials.domain;

public class TWUser {

    private Long twUserId;
    private String registrationCode;
    private Long customerId;
    private String email;
    private Boolean active;

    public TWUser(Long twUserId, String registrationCode, Long customerId, String email, Boolean active) {
        this.twUserId = twUserId;
        this.registrationCode = registrationCode;
        this.customerId = customerId;
        this.email = email;
        this.active = active;
    }

    public Long getTwUserId() {
        return twUserId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getActive() {
        return active;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public TWUser withCustomerId(final Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public TWUser withRegistrationCode(final String registrationCode) {
        this.registrationCode = registrationCode;
        return this;
    }
}
