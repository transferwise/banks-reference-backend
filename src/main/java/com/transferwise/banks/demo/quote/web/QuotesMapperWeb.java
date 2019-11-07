package com.transferwise.banks.demo.quote.web;

import com.transferwise.banks.demo.quote.domain.CreateAnonymousQuote;
import com.transferwise.banks.demo.quote.domain.CreateQuote;
import com.transferwise.banks.demo.values.Value;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
class QuotesMapperWeb {

    public CreateAnonymousQuote mapToCreateAnonymousQuote(AnonymousQuoteRequest anonymousQuoteRequest) {
        return new CreateAnonymousQuote(anonymousQuoteRequest.getSourceCurrency().get(),
                anonymousQuoteRequest.getTargetCurrency().get(),
                getOrNull(anonymousQuoteRequest.getSourceAmount()),
                getOrNull(anonymousQuoteRequest.getTargetAmount()));
    }

    public CreateQuote mapToCreateQuote(QuoteRequest quoteRequest) {
        return new CreateQuote(quoteRequest.getProfile().get(),
                quoteRequest.getSourceCurrency().get(),
                quoteRequest.getTargetCurrency().get(),
                getOrNull(quoteRequest.getSourceAmount()),
                getOrNull(quoteRequest.getTargetAmount()));
    }

    private <T> T getOrNull(final Value<T> value) {
        return ofNullable(value).map(Value::get).orElse(null);
    }
}
