package com.transferwise.banks.demo.quote.web;

import com.transferwise.banks.demo.client.params.TargetAccount;
import com.transferwise.banks.demo.quote.Quote;
import com.transferwise.banks.demo.quote.domain.CreateQuote;
import com.transferwise.banks.demo.quote.domain.QuotesService;
import com.transferwise.banks.demo.transfer.domain.TransferSummary;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/quotes")
public class QuotesController {

    private final QuotesService quotesService;
    private final QuotesMapperWeb quotesMapperWeb;

    public QuotesController(QuotesService quotesService, QuotesMapperWeb quotesMapperWeb) {
        this.quotesService = quotesService;
        this.quotesMapperWeb = quotesMapperWeb;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Publisher<Quote> create(@Valid @RequestBody final QuoteRequest quoteRequest,
                                   @RequestParam final Long customerId) {

        CreateQuote createQuote = quotesMapperWeb.mapToCreateQuote(quoteRequest);
        return quotesService.createQuote(customerId, createQuote);

        /*final var customer = customers.find(customerId);
        return twQuote
                .create(customer, quoteRequest)
                .doOnSuccess(quote -> customers.save(customer.addQuote(quote)));*/
    }

    @PatchMapping(produces = APPLICATION_JSON_VALUE)
    public Publisher<TransferSummary> patch(@RequestParam Long customerId,
                                            @RequestParam final UUID quoteId,
                                            @RequestParam final TargetAccount targetAccount) {
        return quotesService.updateQuote(customerId, quoteId, targetAccount);
    }
}
