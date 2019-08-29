package com.transferwise.t4b.customer;

import com.transferwise.t4b.client.params.Email;
import com.transferwise.t4b.client.params.PersonalProfileId;
import com.transferwise.t4b.credentials.Credentials;
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
    private Credentials credentials;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Profile personalProfile;

    @OneToOne(cascade = CascadeType.ALL)
    private Profile businessProfile;

    @ElementCollection(fetch = EAGER)
    private final List<UUID> quoteIds = new ArrayList<>();

    protected Customer() {
    }

    public Customer(final NewCustomer newCustomer) {
        firstName = newCustomer.getFirstName();
        lastName = newCustomer.getLastName();
        email = newCustomer.getEmail();
    }

    public boolean hasExpiredCredentials() {
        return credentials.areExpired();
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

    public Profile getPersonalProfile() {
        return personalProfile;
    }

    public Profile getBusinessProfile() {
        return businessProfile;
    }

    public Customer withUser(final User user) {
        this.user = user.updated();
        return this;
    }

    public Customer withCredentials(final Credentials credentials) {
        this.credentials = credentials;
        return this;
    }

    public Customer withPersonalProfile(final Profile personalProfile) {
        this.personalProfile = personalProfile;
        return this;
    }

    public Customer withProfiles(final List<Profile> profiles) {
        profiles.forEach(profile -> {
            if (profile.isPersonal()) {
                personalProfile = profile;
            } else {
                businessProfile = profile;
            }
        });

        return this;
    }

    public Customer addQuote(final Quote quote) {
        quoteIds.add(quote.getId());
        return this;
    }

    public PersonalProfileId personalProfileId() {
        return new PersonalProfileId(personalProfile.getId());
    }

    public UUID latestQuoteId() {
        if (quoteIds.isEmpty()) {
            return null;
        }

        return quoteIds.get(quoteIds.size() - 1);
    }
}
