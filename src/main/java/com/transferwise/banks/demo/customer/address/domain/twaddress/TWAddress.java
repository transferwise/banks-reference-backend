package com.transferwise.banks.demo.customer.address.domain.twaddress;

import com.transferwise.banks.demo.customer.address.domain.Address;
import com.transferwise.banks.demo.customer.address.domain.twaddress.twoccupation.TWOccupation;
import com.transferwise.banks.demo.customer.address.occupation.domain.Occupation;

import java.util.ArrayList;
import java.util.List;

public class TWAddress {

    private String firstLine;
    private String postCode;
    private String city;
    private String country;
    private String state;
    private List<TWOccupation> occupations;

    public TWAddress() {}

    public TWAddress(Address address) {
        this.firstLine = address.getFirstLine();
        this.postCode = address.getPostCode();
        this.city = address.getCity();
        this.state = address.getState();
        this.country = address.getCountry();
        this.occupations = mapToTWOccupation(address.getOccupations());
    }

    public String getFirstLine() { return firstLine; }
    public String getPostCode() { return postCode; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getCountry() { return country; }
    public List<TWOccupation> getOccupations() { return occupations; }

    private List<TWOccupation> mapToTWOccupation(List<Occupation> occupations) {
        List<TWOccupation> twOccupations = new ArrayList<>();

        for(Occupation occupation: occupations) {
            twOccupations.add(new TWOccupation(occupation));
        }

        return twOccupations;
    }
}
