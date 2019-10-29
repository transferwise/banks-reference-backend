package com.transferwise.banks.demo.quote.domain;

import com.transferwise.banks.demo.client.params.TargetAccount;
import com.transferwise.banks.demo.quote.Quote;
import com.transferwise.banks.demo.transfer.domain.TransferSummary;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface QuotesService {

    Mono<Quote> createAnonymousQuote(CreateAnonymousQuote createAnonymousQuote);

    Mono<Quote> createQuote(Long customerId, CreateQuote createQuote);

    Mono<TransferSummary> updateQuote(Long customerId, UUID quoteId, TargetAccount targetAccount);

    Mono<Quote> getQuote(Long customerId, UUID quoteUuid);
}
