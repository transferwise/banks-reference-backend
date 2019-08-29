package com.transferwise.t4b.customer;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity(name = "profiles")
public class Profile {

    @Id
    private Long id;
    private String type;
    private ZonedDateTime createdAt;

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    Boolean isPersonal() {
        return "personal".equalsIgnoreCase(type);
    }
}
