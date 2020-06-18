package com.transferwise.banks.demo.customer.address.domain;

import com.transferwise.banks.demo.customer.address.occupation.domain.Occupation;
import com.transferwise.banks.demo.customer.domain.Customer;

import java.util.List;

public class Address {

    private Long id;
    private String firstLine;
    private String postCode;
    private String city;
    private String state;
    private String country;
    private Long customerId;

    private List<Occupation> occupations;

    public Address() {}

    public Address(Long id, String firstLine, String postCode, String city, String state, String country, Long customerId) {
        this.id = id;
        this.firstLine = firstLine;
        this.postCode = postCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.customerId = customerId;
    }

    public Address(String firstLine, String postCode, String city, String state, String country, Long customerId) {
        this.firstLine = firstLine;
        this.postCode = postCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.customerId = customerId;
    }

    public Address(Long id, String firstLine, String postCode, String city, String state, String country, Long customerId, List<Occupation> occupations) {
        this.id = id;
        this.firstLine = firstLine;
        this.postCode = postCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.customerId = customerId;
        this.occupations = occupations;
    }

    public Address(String firstLine, String postCode, String city, String state, String country, Long customerId, List<Occupation> occupations) {
        this.firstLine = firstLine;
        this.postCode = postCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.customerId = customerId;
        this.occupations = occupations;
    }

    public Long getId() { return id; }
    public String getFirstLine() { return firstLine; }
    public String getPostCode() { return postCode; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getCountry() { return country; }
    public Long getCustomerId() { return customerId; }
    public List<Occupation> getOccupations() { return occupations; }

    public void setOccupations(List<Occupation> occupations) { this.occupations = occupations; }
}
