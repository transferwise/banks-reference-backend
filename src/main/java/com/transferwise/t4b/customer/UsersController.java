package com.transferwise.t4b.customer;

import com.transferwise.t4b.client.ApiClient;
import com.transferwise.t4b.exceptions.ResourceNotFoundException;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final ApiClient client;
    private final CustomerRepository customers;

    public UsersController(final ApiClient client, final CustomerRepository customers) {
        this.client = client;
        this.customers = customers;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Publisher<User> create(@RequestParam final Long id) {
        final var customer = customers.findById(id).orElseThrow(() -> new ResourceNotFoundException());

        return client.createUser(customer)
                .map(user -> customers.save(customer.withUserCreatedByUs(user)))
                .map(Customer::getUser);
    }
}
