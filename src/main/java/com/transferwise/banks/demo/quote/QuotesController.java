package com.transferwise.banks.demo.quote;

import com.transferwise.banks.demo.customer.CustomersRepository;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/quotes")
public class QuotesController {

    private final TransferWiseQuote twQuote;
    private final CustomersRepository customers;

    public QuotesController(final TransferWiseQuote twQuote, final CustomersRepository customers) {
        this.twQuote = twQuote;
        this.customers = customers;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Publisher<Quote> create(@Valid @RequestBody final QuoteRequest quoteRequest,
                                   @RequestParam final Long customerId) {
        final var customer = customers.find(customerId);
        return twQuote
                .create(customer, quoteRequest)
                .doOnSuccess(quote -> customers.save(customer.addQuote(quote)));
    }
}
