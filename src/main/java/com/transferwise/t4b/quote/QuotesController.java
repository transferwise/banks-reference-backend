package com.transferwise.t4b.quote;

import com.transferwise.t4b.client.ApiClient;
import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.CustomerRepository;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/quotes")
public class QuotesController {

    private final ApiClient client;
    private final CustomerRepository customers;

    public QuotesController(final ApiClient client, final CustomerRepository customers) {
        this.client = client;
        this.customers = customers;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Publisher<Quote> create(@Valid @RequestBody final QuoteRequest quoteRequest,
                                   @PathVariable final Long customerId) {
        final var customer = customers.find(customerId);
        return createQuote(customer, quoteRequest);
    }

    @GetMapping("/{quoteId}")
    public Publisher<Quote> show(@PathVariable final Long quoteId) {
        return customers
                .findById(1L)
                .map(customer -> client.quote(customer.accessToken(), quoteId))
                .get();
    }

    private Publisher<Quote> createQuote(final Customer customer, final QuoteRequest quoteRequest) {
        return client.quote(customer.accessToken(), quoteRequest);
    }
}
