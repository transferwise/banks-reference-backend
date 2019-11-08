package com.transferwise.banks.demo.quote.domain;

import com.transferwise.banks.demo.client.params.TargetAccount;
import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import com.transferwise.banks.demo.credentials.persistence.twprofile.TWProfilePersistence;
import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
class QuotesServiceImpl implements QuotesService {

    private final CredentialsManager credentialsManager;
    private final QuotesTWClient quotesTWClient;
    private final TWProfilePersistence twProfilePersistence;

    QuotesServiceImpl(CredentialsManager credentialsManager, QuotesTWClient quotesTWClient, TWProfilePersistence twProfilePersistence) {
        this.credentialsManager = credentialsManager;
        this.quotesTWClient = quotesTWClient;
        this.twProfilePersistence = twProfilePersistence;
    }

    @Override
    public Mono<Quote> createAnonymousQuote(CreateAnonymousQuote createAnonymousQuote) {
        return quotesTWClient.createAnonymousQuote(createAnonymousQuote);
    }

    @Override
    public Mono<Quote> createQuote(Long customerId, CreateQuote createQuote) {
        return twProfilePersistence.findByCustomerId(customerId)
                .map(twProfile -> credentialsManager.refreshTokens(customerId)
                        .flatMap(twUserTokens -> quotesTWClient.createQuote(twUserTokens, createQuote.withProfileId(twProfile.getTwProfileId()))))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Mono<Quote> updateQuote(Long customerId, UUID quoteId, TargetAccount targetAccount) {
        return twProfilePersistence.findByCustomerId(customerId)
                .map(twProfile -> credentialsManager.refreshTokens(customerId)
                        .flatMap(twUserTokens -> quotesTWClient.updateQuote(twUserTokens, quoteId, twProfile.getTwProfileId(), targetAccount)))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Mono<Quote> getQuote(Long customerId, UUID quoteUuid) {
        return credentialsManager.refreshTokens(customerId)
                .flatMap(twUserTokens -> quotesTWClient.getQuote(twUserTokens, quoteUuid));
    }


}
