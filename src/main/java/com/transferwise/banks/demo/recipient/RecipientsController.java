package com.transferwise.banks.demo.recipient;

import com.transferwise.banks.demo.customer.CustomersRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/recipients")
public class RecipientsController {

    private final TransferWiseRecipients recipients;
    private final CustomersRepository customers;

    public RecipientsController(final TransferWiseRecipients recipients, final CustomersRepository customers) {
        this.recipients = recipients;
        this.customers = customers;
    }

    @GetMapping
    public Publisher<Recipient> index(@RequestParam final Long customerId) {
        return customers
                .findById(customerId)
                .map(recipients::all)
                .orElse(Flux.empty());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces =  APPLICATION_JSON_VALUE)
    public Mono<String> create(@RequestParam final Long customerId, final HttpEntity<String> rawRequest) {
        final var customer = customers.find(customerId);
        return recipients.create(customer, rawRequest.getBody());
    }
}
