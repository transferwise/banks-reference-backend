package com.transferwise.t4b.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class NewCustomer {

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
