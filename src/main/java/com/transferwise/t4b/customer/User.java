package com.transferwise.t4b.customer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.now;

@Entity
@Table(name = "users")
public class User {

    @Id
    private Long id;
    private String email;
    private Boolean active;
    private Boolean createdByUs;
    private ZonedDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getCreatedByUs() {
        return createdByUs;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public User createdByUs() {
        createdByUs = true;
        updatedAt = now();
        return this;
    }
}
