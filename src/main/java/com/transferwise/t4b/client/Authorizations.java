package com.transferwise.t4b.client;

import com.transferwise.t4b.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class Authorizations {

    private final TransferWiseBankConfig config;

    Authorizations(final TransferWiseBankConfig config) {
        this.config = config;
    }

    public String basic() {
        return String.format("Basic %s", config.encodedCredentials());
    }

    String bearer(final Customer customer) {
        return bearer(customer.accessToken());
    }

    String bearer(final String token) {
        return String.format("Bearer %s", token);
    }
}
