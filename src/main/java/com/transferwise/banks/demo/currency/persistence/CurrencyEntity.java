package com.transferwise.banks.demo.currency.persistence;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity(name = "currencies")
class CurrencyEntity {

    @Id
    private String code;
    private String name;
    private Boolean mostPopular;

    @ElementCollection(fetch = EAGER)
    private List<String> countries = new ArrayList<>();

    public CurrencyEntity() {
        this(null, null, false);
    }

    public CurrencyEntity(final String code, final String name, final Boolean mostPopular) {
        this.code = code;
        this.name = name;
        this.mostPopular = mostPopular;
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
        return Collections.unmodifiableList(countries);
    }
}
