package com.transferwise.banks.demo.recipient;

import com.transferwise.banks.demo.customer.CustomersRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/recipient/requirements")
public class RecipientRequirementsController {

    private final TransferWiseRecipients twRecipients;
    private final CustomersRepository customers;

    public RecipientRequirementsController(final TransferWiseRecipients twRecipients, final CustomersRepository customers) {
        this.twRecipients = twRecipients;
        this.customers = customers;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Publisher<String> get(@RequestParam final Long customerId) {
        final var customer = customers.find(customerId);
        return twRecipients.requirements(customer);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Publisher<String> create(@RequestParam final Long customerId, final HttpEntity<String> rawRequest) {
        final var customer = customers.find(customerId);
        return twRecipients.requirements(customer, rawRequest.getBody());
    }
}
