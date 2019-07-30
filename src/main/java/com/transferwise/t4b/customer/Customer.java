package com.transferwise.t4b.customer;

import com.transferwise.t4b.client.Credentials;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Customer {

    @Id
    private Long id;

    private final String name;

    @OneToOne(cascade = CascadeType.ALL)
    public Credentials credentials;

    private Customer() {
        this(null);
    }

    public Customer(final String name) {
        this.name = name;
    }

    public boolean hasCredentials() {
        return credentials != null && !credentials.isEmpty();
    }
}
