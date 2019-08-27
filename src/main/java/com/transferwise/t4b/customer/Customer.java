package com.transferwise.t4b.customer;

import com.transferwise.t4b.client.params.Email;
import com.transferwise.t4b.credentials.Credentials;

import javax.persistence.*;

@Entity(name = "customers")
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Credentials credentials;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    protected Customer() {
    }

    public Customer(final NewCustomer newCustomer) {
        name = newCustomer.getName();
        email = newCustomer.getEmail();
    }

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

    public Customer withUser(final User user) {
        this.user = user.updated();
        return this;
    }

    public Customer withCredentials(final Credentials credentials) {
        this.credentials = credentials;
        return this;
    }
}
