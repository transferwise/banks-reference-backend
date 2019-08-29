package com.transferwise.t4b.customer;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity(name = "profiles")
public class Profile {

    @Id
    private final Long id;
    private final String type;
    private final ZonedDateTime createdAt;

    public Profile() {
        this(null, null, null);
    }

    public Profile(final Long id, final String type, final ZonedDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.createdAt = createdAt;
    }

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
