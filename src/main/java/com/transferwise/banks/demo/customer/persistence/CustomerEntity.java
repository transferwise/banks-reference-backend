package com.transferwise.banks.demo.customer.persistence;

import com.transferwise.banks.demo.client.params.Email;
import com.transferwise.banks.demo.customer.persistence.address.AddressEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "customers")
public class CustomerEntity {

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
    @JoinColumn(name = "addressId", referencedColumnName = "id")
    private AddressEntity address;

    public CustomerEntity() {
    }

    public CustomerEntity(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String email, AddressEntity address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
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

    public AddressEntity getAddress() { return address; }
}
