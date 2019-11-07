package com.transferwise.banks.demo.currency.domain;

import java.util.List;

public class Currency {

    private String code;
    private String name;
    private Boolean mostPopular;
    private List<String> countries;

    public Currency() {
    }

    public Currency(String code, String name, Boolean mostPopular, List<String> countries) {
        this.code = code;
        this.name = name;
        this.mostPopular = mostPopular;
        this.countries = countries;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Boolean getMostPopular() {
        return mostPopular;
    }

    public List<String> getCountries() {
        return countries;
    }
}
