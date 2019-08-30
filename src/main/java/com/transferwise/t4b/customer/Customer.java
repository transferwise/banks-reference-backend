package com.transferwise.t4b.customer;

import com.transferwise.t4b.client.params.Email;
import com.transferwise.t4b.client.params.ProfileId;
import com.transferwise.t4b.credentials.TransferwiseCredentials;
import com.transferwise.t4b.quote.Quote;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.FetchType.EAGER;

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
    private TransferwiseCredentials credentials;

    @OneToOne(cascade = CascadeType.ALL)
    private TransferwiseUser user;

    @OneToOne(cascade = CascadeType.ALL)
    private TransferwiseProfile profile;

    @ElementCollection(fetch = EAGER)
    private final List<UUID> quoteIds = new ArrayList<>();

    public Customer() {
    }

    public Customer(final NewCustomer newCustomer) {
        firstName = newCustomer.getFirstName();
        lastName = newCustomer.getLastName();
        email = newCustomer.getEmail();
        dateOfBirth = newCustomer.getDateOfBirth();
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

    public TransferwiseCredentials getCredentials() {
        return credentials;
    }

    public TransferwiseUser getUser() {
        return user;
    }

    public TransferwiseProfile getProfile() {
        return profile;
    }

    public Customer withUser(final TransferwiseUser user) {
        this.user = user.updated();
        return this;
    }

    public Customer withCredentials(final TransferwiseCredentials credentials) {
        this.credentials = credentials;
        return this;
    }

    public Customer withPersonalProfile(final TransferwiseProfile profile) {
        this.profile = profile;
        return this;
    }

    public Customer withProfiles(final List<TransferwiseProfile> profiles) {
        profile = profiles
                .stream()
                .filter(TransferwiseProfile::isPersonal)
                .findFirst()
                .orElse(null);
        return this;
    }

    public Customer addQuote(final Quote quote) {
        quoteIds.add(quote.getId());
        return this;
    }

    public ProfileId profileId() {
        return new ProfileId(profile.getId());
    }

    public UUID latestQuoteId() {
        if (quoteIds.isEmpty()) {
            return null;
        }

        return quoteIds.get(quoteIds.size() - 1);
    }
}
