package com.transferwise.banks.demo.customer.persistence;

import com.transferwise.banks.demo.client.params.Email;
import com.transferwise.banks.demo.client.params.ProfileId;
import com.transferwise.banks.demo.credentials.TransferwiseCredentials;
import com.transferwise.banks.demo.credentials.TransferwiseProfile;
import com.transferwise.banks.demo.credentials.TransferwiseUser;
import com.transferwise.banks.demo.customer.CustomerTransfer;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity(name = "customers")
class CustomerEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    /*@OneToOne(cascade = ALL)
    private TransferwiseCredentials credentials;*/

    @OneToOne(cascade = ALL)
    private TransferwiseUser user;

    @OneToOne(cascade = ALL)
    private TransferwiseProfile profile;

    @OneToMany(cascade = ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private final List<CustomerTransfer> customerTransfers = new ArrayList<>();

    public CustomerEntity() {
    }

    public CustomerEntity(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /*public String accessToken() {
        return credentials.accessToken;
    }*/

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

    /*public TransferwiseCredentials getCredentials() {
        return credentials;
    }*/

    public TransferwiseUser getUser() {
        return user;
    }

    public TransferwiseProfile getProfile() {
        return profile;
    }

    public List<CustomerTransfer> getCustomerTransfers() {
        return customerTransfers;
    }

    public CustomerEntity withUser(final TransferwiseUser user) {
        this.user = user.updated();
        return this;
    }

    /*public CustomerEntity withCredentials(final TransferwiseCredentials credentials) {
        this.credentials = credentials;
        return this;
    }*/

    public CustomerEntity withPersonalProfile(final TransferwiseProfile profile) {
        this.profile = profile;
        return this;
    }

    public CustomerEntity withProfiles(final List<TransferwiseProfile> profiles) {
        profile = profiles
                .stream()
                .filter(TransferwiseProfile::isPersonal)
                .findFirst()
                .orElse(null);
        return this;
    }

    public ProfileId profileId() {
        return new ProfileId(profile.getId());
    }

    public CustomerEntity addCustomerTransfer(final CustomerTransfer customerTransfer) {
        customerTransfers.add(customerTransfer);
        return this;
    }
}
