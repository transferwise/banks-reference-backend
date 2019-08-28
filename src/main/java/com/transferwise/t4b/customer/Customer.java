package com.transferwise.t4b.customer;

import com.transferwise.t4b.client.params.Email;
import com.transferwise.t4b.credentials.Credentials;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "customers")
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Credentials credentials;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

    protected Customer() {
    }

    public Customer(final NewCustomer newCustomer) {
        firstName = newCustomer.getFirstName();
        lastName = newCustomer.getLastName();
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public User getUser() {
        return user;
    }

    public Profile getProfile() {
        return profile;
    }

    public Customer withUser(final User user) {
        this.user = user.updated();
        return this;
    }

    public Customer withCredentials(final Credentials credentials) {
        this.credentials = credentials;
        return this;
    }

    public Customer withProfile(final Profile profile) {
        this.profile = profile;
        return this;
    }
}
