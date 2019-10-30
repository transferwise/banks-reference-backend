package com.transferwise.banks.demo.credentials.persistence.twuser;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tw_user")
class TWUserEntity {


    private Long twUserId;
    private String registrationCode;

    @Id
    private Long customerId;

    private String email;
    private Boolean active;

    public TWUserEntity() {
    }

    public TWUserEntity(Long twUserId, String registrationCode, Long customerId, String email, Boolean active) {
        this.twUserId = twUserId;
        this.registrationCode = registrationCode;
        this.customerId = customerId;
        this.email = email;
        this.active = active;
    }

    public Long getTwUserId() {
        return twUserId;
    }

    public String getRegistrationCode() {
        return registrationCode;
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
}
