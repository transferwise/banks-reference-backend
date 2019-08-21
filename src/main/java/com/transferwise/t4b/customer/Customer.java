package com.transferwise.t4b.customer;

import com.transferwise.t4b.client.Credentials;
import com.transferwise.t4b.client.params.Email;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Customer {

    @Id
    private Long id;
    private String name;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    public Credentials credentials;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public boolean hasExpiredCredentials() {
        return credentials.isExpired();
    }

    public String accessToken() {
        return credentials.accessToken;
    }

    public Email email() {
        return new Email(email);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public User getUser() {
        return user;
    }

    public Customer withUserCreatedByUs(final User user) {
        this.user = user.createdByUs();
        return this;
    }
}
