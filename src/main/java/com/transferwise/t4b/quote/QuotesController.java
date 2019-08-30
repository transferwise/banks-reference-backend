package com.transferwise.t4b.quote;

import com.transferwise.t4b.client.ApiClient;
import com.transferwise.t4b.customer.CustomersRepository;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/quotes")
public class QuotesController {

    private final ApiClient client;
    private final CustomersRepository customers;

    public QuotesController(final ApiClient client, final CustomersRepository customers) {
        this.client = client;
        this.customers = customers;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Publisher<Quote> create(@Valid @RequestBody final QuoteRequest quoteRequest,
                                   @RequestParam final Long customerId) {
        final var customer = customers.find(customerId);
        return client.quote(customer, quoteRequest)
                .doOnSuccess(quote -> customers.save(customer.addQuote(quote)));
    }
}
