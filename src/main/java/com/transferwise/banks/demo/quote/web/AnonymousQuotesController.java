package com.transferwise.banks.demo.quote.web;

import com.transferwise.banks.demo.quote.domain.CreateAnonymousQuote;
import com.transferwise.banks.demo.quote.domain.Quote;
import com.transferwise.banks.demo.quote.domain.QuotesService;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/anonymous-quotes")
public class AnonymousQuotesController {

    private final QuotesService quotesService;

    public AnonymousQuotesController(final QuotesService quotesService) {
        this.quotesService = quotesService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Publisher<QuoteResponse> create(@Valid @RequestBody final QuoteRequest quoteRequest) {
        return quotesService.createAnonymousQuote(mapToCreateAnonymousQuote(quoteRequest))
                .map(this::mapToQuoteResponse);
    }

    private CreateAnonymousQuote mapToCreateAnonymousQuote(QuoteRequest quoteRequest) {
        return new CreateAnonymousQuote(quoteRequest.getSourceCurrency().get(),
                quoteRequest.getTargetCurrency().get(),
                quoteRequest.getSourceAmount(),
                quoteRequest.getTargetAmount());
    }

    private QuoteResponse mapToQuoteResponse(Quote quote) {
        return new QuoteResponse(quote.getId(),
                quote.getSourceCurrency(),
                quote.getTargetCurrency(),
                quote.getSourceAmount(),
                quote.getTargetAmount(),
                quote.getRate(),
                quote.getFee(),
                quote.getFormattedEstimatedDelivery(),
                quote.getRateType(),
                quote.getNotices());
    }
}
