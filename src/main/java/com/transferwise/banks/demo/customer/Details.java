package com.transferwise.banks.demo.customer;

public final class Details {
    public final String firstName;
    public final String lastName;
    public final String dateOfBirth;
    public final String phoneNumber;

    public Details(final Customer customer) {
        firstName = customer.getFirstName();
        lastName = customer.getLastName();
        dateOfBirth = customer.getDateOfBirth().toString();
        phoneNumber = customer.getPhoneNumber();
    }
}
