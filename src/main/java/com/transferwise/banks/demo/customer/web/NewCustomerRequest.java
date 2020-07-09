package com.transferwise.banks.demo.customer.web;

import com.fasterxml.jackson.annotation.JsonFormat;
<<<<<<< HEAD
=======
import com.transferwise.banks.demo.customer.web.address.NewAddressRequest;
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

class NewCustomerRequest {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotNull
    private String phoneNumber;

<<<<<<< HEAD
    public NewCustomerRequest() {
    }

    public NewCustomerRequest(@NotNull final String firstName, @NotNull final String lastName, @NotNull @Email final String email, @NotNull final LocalDate dateOfBirth, @NotNull String phoneNumber) {
=======
    @NotNull
    private NewAddressRequest address;

    public NewCustomerRequest() {
    }

    public NewCustomerRequest(@NotNull final String firstName, @NotNull final String lastName, @NotNull @Email final String email, @NotNull final LocalDate dateOfBirth, @NotNull String phoneNumber, @NotNull NewAddressRequest address) {
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
<<<<<<< HEAD
=======
        this.address = address;
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
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

    public NewAddressRequest getAddress() { return address; }
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
}
