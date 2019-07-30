package com.transferwise.t4b.customer;

import java.time.LocalDate;

public class Profile {

    public Long id;
    public String type;

    class Details {
        String firstName;
        String lastName;
        LocalDate dateOfBirth;
    }
}
