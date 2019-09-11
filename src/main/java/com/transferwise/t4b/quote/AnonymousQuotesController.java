package com.transferwise.t4b.quote;

import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/anonymous-quotes")
public class AnonymousQuotesController {

    private final TransferWiseQuote twQuote;

    public AnonymousQuotesController(final TransferWiseQuote twQuote) {
        this.twQuote = twQuote;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Publisher<Quote> create(@Valid @RequestBody final QuoteRequest quoteRequest) {
        return twQuote.createAnonymous(quoteRequest);
    }
}
