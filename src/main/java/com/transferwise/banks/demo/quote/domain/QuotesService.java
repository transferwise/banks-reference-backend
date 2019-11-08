package com.transferwise.banks.demo.quote.domain;

import com.transferwise.banks.demo.client.params.TargetAccount;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface QuotesService {

    Mono<Quote> createAnonymousQuote(CreateAnonymousQuote createAnonymousQuote);

    Mono<Quote> createQuote(Long customerId, CreateQuote createQuote);

    Mono<Quote> updateQuote(Long customerId, UUID quoteId, TargetAccount targetAccount);

    Mono<Quote> getQuote(Long customerId, UUID quoteUuid);
}
