package com.transferwise.t4b.customer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Profile {

    public final Long id;
    public final String type;
    public final Details details;

    @JsonCreator
    public Profile(@JsonProperty("id") final Long id,
                   @JsonProperty("type") final String type) {
        this.id = id;
        this.type = type;
        details = new Details();
    }

    class Details {
        public final String firstName;
        public final String lastName;
        public final LocalDate dateOfBirth;
        public final String name;
        public final String businessCategory;
        public final String webpage;

        public Details() {
            this(null, null, null, null, null, null);
        }

        public Details(@JsonProperty("firstName") final String firstName,
                       @JsonProperty("lastName") final String lastName,
                       @JsonProperty("dateOfBirth") final LocalDate dateOfBirth,
                       @JsonProperty("name") final String name,
                       @JsonProperty("businessCategory") final String businessCategory,
                       @JsonProperty("webpage") final String webpage) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.dateOfBirth = dateOfBirth;
            this.name = name;
            this.businessCategory = businessCategory;
            this.webpage = webpage;
        }
    }
}
