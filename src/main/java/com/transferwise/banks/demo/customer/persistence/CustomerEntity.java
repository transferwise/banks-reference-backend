package com.transferwise.banks.demo.customer.persistence;

import com.transferwise.banks.demo.client.params.Email;
<<<<<<< HEAD

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "customers")
class CustomerEntity {
=======
import com.transferwise.banks.demo.customer.persistence.address.AddressEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "customers")
public class CustomerEntity {
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;

    @Column(unique = true)
    private String email;

<<<<<<< HEAD
    public CustomerEntity() {
    }

    public CustomerEntity(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String email) {
=======
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", referencedColumnName = "id")
    private AddressEntity address;

    public CustomerEntity() {
    }

    public CustomerEntity(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String email, AddressEntity address) {
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
<<<<<<< HEAD
=======
        this.address = address;
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
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
<<<<<<< HEAD
=======

    public AddressEntity getAddress() { return address; }
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
}
