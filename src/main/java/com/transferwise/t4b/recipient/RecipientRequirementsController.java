package com.transferwise.t4b.recipient;

import com.transferwise.t4b.client.ApiClient;
import com.transferwise.t4b.customer.CustomersRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/recipient/requirements")
public class RecipientRequirementsController {

    private final ApiClient client;
    private final CustomersRepository customers;

    public RecipientRequirementsController(final ApiClient client, final CustomersRepository customers) {
        this.client = client;
        this.customers = customers;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Publisher<String> get(@RequestParam final Long customerId) {
        final var customer = customers.find(customerId);
        return client.recipientRequirements(customer);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Publisher<String> create(@RequestParam final Long customerId, final HttpEntity<String> rawRequest) {
        final var customer = customers.find(customerId);
        return client.recipientRequirements(customer, rawRequest.getBody());
    }
}
