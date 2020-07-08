package com.transferwise.banks.demo.customer.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.transferwise.banks.demo.customer.web.address.NewAddressRequest;

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

    @NotNull
    private NewAddressRequest address;

    public NewCustomerRequest() {
    }

    public NewCustomerRequest(@NotNull final String firstName, @NotNull final String lastName, @NotNull @Email final String email, @NotNull final LocalDate dateOfBirth, @NotNull String phoneNumber, @NotNull NewAddressRequest address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
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

    public NewAddressRequest getAddress() { return address; }
}
