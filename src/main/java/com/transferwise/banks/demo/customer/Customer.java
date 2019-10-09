package com.transferwise.banks.demo.customer;

import com.transferwise.banks.demo.client.params.Email;
import com.transferwise.banks.demo.client.params.ProfileId;
import com.transferwise.banks.demo.credentials.TransferwiseCredentials;
import com.transferwise.banks.demo.credentials.TransferwiseProfile;
import com.transferwise.banks.demo.credentials.TransferwiseUser;
import com.transferwise.banks.demo.quote.Quote;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

import static javax.persistence.CascadeType.ALL;
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

    @OneToOne(cascade = ALL)
    private TransferwiseCredentials credentials;

    @OneToOne(cascade = ALL)
    private TransferwiseUser user;

    @OneToOne(cascade = ALL)
    private TransferwiseProfile profile;

    @ElementCollection(fetch = EAGER)
    private final List<UUID> quoteIds = new ArrayList<>();

    @OneToMany(cascade = ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private final Set<CustomerTransfer> customerTransfers = new HashSet<>();

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

    public Set<CustomerTransfer> getCustomerTransfers() {
        return customerTransfers;
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

    public Customer addCustomerTransfer(final CustomerTransfer customerTransfer) {
        customerTransfers.add(customerTransfer);
        return this;
    }
}
