package com.transferwise.banks.demo.quote.domain;

import com.transferwise.banks.demo.client.params.TargetAccount;
import com.transferwise.banks.demo.quote.Quote;
import com.transferwise.banks.demo.transfer.TransferSummary;
import org.reactivestreams.Publisher;

import java.util.UUID;

public interface QuotesService {

    Publisher<Quote> createAnonymousQuote(CreateAnonymousQuote createAnonymousQuote);

    Publisher<Quote> createQuote(Long customerId, CreateQuote createQuote);

    Publisher<TransferSummary> updateQuote(Long customerId, UUID quoteId, TargetAccount targetAccount);
}
