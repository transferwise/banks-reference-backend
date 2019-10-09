package com.transferwise.banks.demo.credentials;

import com.transferwise.banks.demo.client.JsonParser;
import com.transferwise.banks.demo.customer.Customer;
import com.transferwise.banks.demo.customer.Details;

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
