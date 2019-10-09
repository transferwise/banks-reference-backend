package com.transferwise.banks.demo.currency;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity(name = "currencies")
public class Currency {

    @Id
    public final String code;
    public final String name;
    public final Boolean mostPopular;

    @ElementCollection(fetch = EAGER)
    private final List<String> countries = new ArrayList<>();

    public Currency() {
        this(null, null, false);
    }

    public Currency(final String code, final String name, final Boolean mostPopular) {
        this.code = code;
        this.name = name;
        this.mostPopular = mostPopular;
    }

    public List<String> getCountries() {
        return Collections.unmodifiableList(countries);
    }
}
