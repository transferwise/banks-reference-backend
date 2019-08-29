package com.transferwise.t4b;

import com.transferwise.t4b.credentials.Credentials;
import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.NewCustomer;
import com.transferwise.t4b.customer.Profile;

import java.time.LocalDate;

import static java.time.ZonedDateTime.now;

public class Fabricator {

    public static Customer newCustomer() {
        final var credentials = new Credentials("", "", 1);
        final var profile = new Profile(1L, "personal", now());
        final var dob = LocalDate.of(1989, 5, 16);

        return new Customer(new NewCustomer("Fulano", "de Tal", "fulano@email.com", dob))
                .withCredentials(credentials)
                .withPersonalProfile(profile);
    }
}
