package com.transferwise.banks.demo.quote;

import com.transferwise.banks.demo.client.params.TargetAccount;
import com.transferwise.banks.demo.transfer.TransferSummary;
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

    public QuotesController(QuotesService quotesService) {
        this.quotesService = quotesService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Publisher<Quote> create(@Valid @RequestBody final QuoteRequest quoteRequest,
                                   @RequestParam final Long customerId) {
        return quotesService.createQuote(customerId, quoteRequest);
    }

    @PatchMapping(produces = APPLICATION_JSON_VALUE)
    public Publisher<TransferSummary> patch(@RequestParam final Long customerId,
                                            @RequestParam final TargetAccount targetAccount,
                                            @RequestParam final UUID quoteId) {
        return quotesService.updateQuote(customerId, targetAccount, quoteId);
    }
}
