package com.transferwise.t4b.customer;

import com.transferwise.t4b.client.Credentials;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class Customer {

    @Id
    private Long id;
    private String name;
    private Boolean createdByUs;
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    public Credentials credentials;

    public boolean hasExpiredCredentials() {
        return credentials.isExpired();
    }

    public String accessToken() {
        return credentials.accessToken;
    }
}
