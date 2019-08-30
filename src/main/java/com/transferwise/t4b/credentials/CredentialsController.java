package com.transferwise.t4b.credentials;

import com.transferwise.t4b.client.ApiClient;
import com.transferwise.t4b.client.params.Code;
import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.CustomerRepository;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credentials")
public class CredentialsController {

    private final ApiClient client;
    private final CustomerRepository customers;

    public CredentialsController(final ApiClient client, final CustomerRepository customers) {
        this.client = client;
        this.customers = customers;
    }

    @PostMapping
    // deeplink, ios android
    public Publisher<Customer> create(@RequestParam final Long customerId, @RequestParam final Code code) {
        final var customer = customers.find(customerId);
        return client
                .attachCredentialsAndProfiles(code, customer)
                .map(customers::save);
    }
}