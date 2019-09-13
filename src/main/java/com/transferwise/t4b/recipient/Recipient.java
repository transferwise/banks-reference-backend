package com.transferwise.t4b.recipient;

public class Recipient {

    private Long id;
    private String currency;
    private boolean active;
    private boolean ownedByCustomer;
    private String type;

    private Name name;

    public Long getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isOwnedByCustomer() {
        return ownedByCustomer;
    }

    public String getType() {
        return type;
    }

    public Name getName() {
        return name;
    }
}
