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

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    public Credentials credentials;

    public boolean hasCredentials() {
        return credentials != null && !credentials.isEmpty();
    }

    public String accessToken() {
        return credentials.accessToken;
    }
}
