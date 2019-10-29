package com.transferwise.banks.demo.quote.web;

import com.transferwise.banks.demo.quote.domain.CreateAnonymousQuote;
import com.transferwise.banks.demo.quote.domain.CreateQuote;
import org.springframework.stereotype.Component;

@Component
class QuotesMapperWeb {

    public CreateAnonymousQuote mapToCreateAnonymousQuote(AnonymousQuoteRequest anonymousQuoteRequest) {
        return new CreateAnonymousQuote(anonymousQuoteRequest.getSourceCurrency().get(),
                anonymousQuoteRequest.getTargetCurrency().get(),
                anonymousQuoteRequest.getSourceAmount().get(),
                anonymousQuoteRequest.getTargetAmount().get());
    }

    public CreateQuote mapToCreateQuote(QuoteRequest quoteRequest) {
        return new CreateQuote(quoteRequest.getProfile().get(),
                quoteRequest.getSourceCurrency().get(),
                quoteRequest.getTargetCurrency().get(),
                quoteRequest.getSourceAmount().get(),
                quoteRequest.getTargetAmount().get());
    }
}
