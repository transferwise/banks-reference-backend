package com.transferwise.t4b.recipient;

public class Recipient {

    private Long id;
    private String currency;
    private boolean active;
    private boolean owndeByCustomer;

    public Long getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isOwndeByCustomer() {
        return owndeByCustomer;
    }
}
