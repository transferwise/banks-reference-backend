package com.transferwise.banks.demo.quote.web;

import com.transferwise.banks.demo.quote.domain.CreateQuote;
import com.transferwise.banks.demo.quote.domain.Quote;
import com.transferwise.banks.demo.quote.domain.QuotesService;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/quotes")
public class QuotesController {

    private final QuotesService quotesService;

    public QuotesController(QuotesService quotesService) {
        this.quotesService = quotesService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Publisher<QuoteResponse> create(@Valid @RequestBody final QuoteRequest quoteRequest,
                                           @RequestParam final Long customerId) {
        CreateQuote createQuote = mapToCreateQuote(quoteRequest);

        return quotesService.createQuote(customerId, createQuote)
                .map(this::mapToQuoteResponse);
    }

    private CreateQuote mapToCreateQuote(QuoteRequest quoteRequest) {
        return new CreateQuote(quoteRequest.getSourceCurrency().get(),
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
