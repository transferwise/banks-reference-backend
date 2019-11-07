package com.transferwise.banks.demo.quote.web;

import com.transferwise.banks.demo.quote.domain.Quote;
import com.transferwise.banks.demo.quote.domain.QuotesService;
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

    private final QuotesService quotesService;
    private final QuotesMapperWeb quotesMapperWeb;

    public AnonymousQuotesController(final QuotesService quotesService, final QuotesMapperWeb quotesMapperWeb) {
        this.quotesService = quotesService;
        this.quotesMapperWeb = quotesMapperWeb;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Publisher<Quote> create(@Valid @RequestBody final AnonymousQuoteRequest anonymousQuoteRequest) {
        return quotesService.createAnonymousQuote(quotesMapperWeb.mapToCreateAnonymousQuote(anonymousQuoteRequest));
    }
}
