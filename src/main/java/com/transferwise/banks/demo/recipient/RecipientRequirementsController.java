package com.transferwise.banks.demo.recipient;

import com.transferwise.banks.demo.customer.CustomersRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public Publisher<List<Map>> get(@RequestParam final Long customerId, @RequestParam final UUID quoteId) {
        final var customer = customers.find(customerId);
        return twRecipients.requirements(customer, quoteId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Publisher<String> create(@RequestParam final Long customerId, final HttpEntity<String> rawRequest, @RequestParam final UUID quoteId) {
        final var customer = customers.find(customerId);
        return twRecipients.requirements(customer, rawRequest.getBody(), quoteId);
    }
}
