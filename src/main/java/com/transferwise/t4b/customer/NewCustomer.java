package com.transferwise.t4b.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class NewCustomer {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Email
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
