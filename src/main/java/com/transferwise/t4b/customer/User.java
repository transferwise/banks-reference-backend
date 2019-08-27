package com.transferwise.t4b.customer;

import com.transferwise.t4b.client.params.Email;
import com.transferwise.t4b.client.params.RegistrationCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.now;
import static java.util.Objects.nonNull;

@Entity
@Table(name = "users")
public class User {

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

    public Boolean getCreatedByUs() {
        return nonNull(registrationCode);
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public User createdByUs() {
        updatedAt = now();
        return this;
    }

    public User withRegistrationCode(final RegistrationCode registrationCode) {
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
