package com.transferwise.t4b.quote;

import com.transferwise.t4b.client.ApiClient;
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

    private final ApiClient client;

    public AnonymousQuotesController(final ApiClient client) {
        this.client = client;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Publisher<Quote> create(@Valid @RequestBody final QuoteRequest quoteRequest) {
        final var a = client.clientCredentials();

        return client.anonymousQuote(quoteRequest);
    }
}
