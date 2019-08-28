package com.transferwise.t4b.customer;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity(name = "profiles")
public class Profile {

    @Id
    private Long id;
    private String type;
    private final ZonedDateTime createdAt;

    public Profile() {
        createdAt = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
