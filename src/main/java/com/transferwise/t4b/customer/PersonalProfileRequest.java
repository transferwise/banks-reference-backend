package com.transferwise.t4b.customer;

import com.transferwise.t4b.client.JsonParser;

public class PersonalProfileRequest {

    public final String type = "personal";
    public final Details details;

    private final JsonParser parser;

    public PersonalProfileRequest(final Customer customer) {
        details = new Details(customer);
        parser = new JsonParser();
    }

    public String toJson() {
        return parser.toJson(this);
    }
}
