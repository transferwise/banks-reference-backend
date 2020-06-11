package com.transferwise.banks.demo.customer.address.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "customer_addresses")
class AddressEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String firstLine;
    private String postCode;
    private String city;
    private String state;
    private String country;

    @Column(unique = true)
    private long customerId;

    public AddressEntity() {
    }

    public AddressEntity(String firstLine, String postCode, String city, String state, String country, Long customerId) {
        this.firstLine = firstLine;
        this.postCode = postCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.customerId = customerId;
    }

    public Long getId() { return id; }
    public String getFirstLine() { return firstLine; }
    public String getPostCode() { return postCode; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getCountry() { return country; }
    public Long getCustomerId() {return customerId; }
}
