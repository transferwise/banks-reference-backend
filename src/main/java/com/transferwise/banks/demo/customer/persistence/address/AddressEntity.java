package com.transferwise.banks.demo.customer.persistence.address;

import com.transferwise.banks.demo.customer.persistence.occupation.OccupationEntity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "customer_addresses")
public class AddressEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String firstLine;
    private String postCode;
    private String city;
    private String state;
    private String country;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "addressId")
    private List<OccupationEntity> occupations;

    public AddressEntity() {
    }

    public AddressEntity(String firstLine, String postCode, String city, String state, String country, List<OccupationEntity> occupations) {
        this.firstLine = firstLine;
        this.postCode = postCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.occupations = occupations;
    }

    public AddressEntity(String firstLine, String postCode, String city, String state, String country) {
        this.firstLine = firstLine;
        this.postCode = postCode;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public Long getId() { return id; }
    public String getFirstLine() { return firstLine; }
    public String getPostCode() { return postCode; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getCountry() { return country; }
    public List<OccupationEntity> getOccupations() { return occupations; }
}
