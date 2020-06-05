package com.transferwise.banks.demo.customer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.transferwise.banks.demo.customer.address.domain.CustomerAddress;
import com.transferwise.banks.demo.customer.address.occupation.domain.Occupation;

import java.time.LocalDate;
import java.util.List;

public class Customer {

    private Long id;
    private String firstName;
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String email;
    private boolean transferWiseAccountLinked;

    private CustomerAddress customerAddress;

    public Customer(Long id, String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Customer(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Customer(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String email, CustomerAddress customerAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.customerAddress = customerAddress;
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

    public CustomerAddress getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(CustomerAddress customerAddress, List<Occupation> occupations) {
        this.customerAddress = customerAddress;
        this.customerAddress.setOccupations(occupations);
    }

    public boolean isTransferWiseAccountLinked() {
        return transferWiseAccountLinked;
    }

    public Customer withTransferWiseAccountLinked(final boolean transferWiseAccountLinked) {
        this.transferWiseAccountLinked = transferWiseAccountLinked;
        return this;
    }
}
