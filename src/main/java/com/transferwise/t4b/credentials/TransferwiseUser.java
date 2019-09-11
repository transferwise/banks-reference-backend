package com.transferwise.t4b.credentials;

import com.transferwise.t4b.client.params.Email;
import com.transferwise.t4b.client.params.RegistrationCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.now;

@Entity
@Table(name = "users")
public class TransferwiseUser {

    @Id
    private Long id;
    private String email;
    private Boolean active;
    private String registrationCode;
    private ZonedDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getActive() {
        return active;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public TransferwiseUser updated() {
        updatedAt = now();
        return this;
    }

    public TransferwiseUser withRegistrationCode(final RegistrationCode registrationCode) {
        this.registrationCode = registrationCode.value();
        return this;
    }

    public RegistrationCode registrationCode() {
        return new RegistrationCode(registrationCode);
    }

    public Email email() {
        return new Email(email);
    }
}
