package com.transferwise.t4b.recipient;

import com.transferwise.t4b.customer.CustomersRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/recipient/requirements")
public class RecipientRequirementsController {

    private final TransferWiseRecipientRequirements twRequirements;
    private final CustomersRepository customers;

    public RecipientRequirementsController(final TransferWiseRecipientRequirements twRequirements, final CustomersRepository customers) {
        this.twRequirements = twRequirements;
        this.customers = customers;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Publisher<String> get(@RequestParam final Long customerId) {
        final var customer = customers.find(customerId);
        return twRequirements.all(customer);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Publisher<String> create(@RequestParam final Long customerId, final HttpEntity<String> rawRequest) {
        final var customer = customers.find(customerId);
        return twRequirements.create(customer, rawRequest.getBody());
    }
}
