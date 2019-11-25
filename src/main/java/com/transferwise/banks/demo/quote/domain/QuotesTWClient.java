package com.transferwise.banks.demo.quote.domain;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface QuotesTWClient {

    Mono<Quote> createAnonymousQuote(CreateAnonymousQuote createAnonymousQuote);

    Mono<Quote> createQuote(TWUserTokens twUserTokens, CreateQuote createQuote);

    Mono<Quote> updateQuote(TWUserTokens twUserTokens, UUID quoteId, Long profileId, Long recipientId);

    Mono<Quote> getQuote(TWUserTokens twUserTokens, UUID quoteId);
}
