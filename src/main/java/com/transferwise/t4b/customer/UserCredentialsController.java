package com.transferwise.t4b.customer;

import com.transferwise.t4b.client.ApiClient;
import com.transferwise.t4b.credentials.Credentials;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-credentials")
public class UserCredentialsController {

    private final ApiClient client;
    private final CustomerRepository customers;

    public UserCredentialsController(final ApiClient client, final CustomerRepository customers) {
        this.client = client;
        this.customers = customers;
    }

    @PostMapping
    public Publisher<Credentials> create(@RequestParam final Long id) {
        final var customer = customers.find(id);
        return client.userCredentials(customer)
                .map(customers::save)
                .map(Customer::getCredentials);
    }
}
