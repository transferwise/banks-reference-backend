package com.transferwise.banks.demo.customer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
<<<<<<< HEAD
=======
import com.transferwise.banks.demo.customer.domain.address.Address;
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc

import java.time.LocalDate;

public class Customer {

    private Long id;
    private String firstName;
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String email;
    private boolean transferWiseAccountLinked;

<<<<<<< HEAD
    public Customer(Long id, String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String email) {
=======
    private Address address;

    public Customer(Long id, String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String email, Address address) {
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
<<<<<<< HEAD
    }

    public Customer(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String email) {
=======
        this.address = address;
    }

    public Customer(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String email, Address address) {
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

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

<<<<<<< HEAD
=======
    public Address getAddress() { return address; }

>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
    public boolean isTransferWiseAccountLinked() {
        return transferWiseAccountLinked;
    }

    public Customer withTransferWiseAccountLinked(final boolean transferWiseAccountLinked) {
        this.transferWiseAccountLinked = transferWiseAccountLinked;
        return this;
    }
}
