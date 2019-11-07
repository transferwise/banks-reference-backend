package com.transferwise.banks.demo.quote.domain;

import com.transferwise.banks.demo.client.params.TargetAccount;
import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import com.transferwise.banks.demo.credentials.persistence.twprofile.TWProfilePersistence;
import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import com.transferwise.banks.demo.recipient.domain.Recipient;
import com.transferwise.banks.demo.recipient.domain.RecipientsService;
import com.transferwise.banks.demo.transfer.domain.TransferSummary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
class QuotesServiceImpl implements QuotesService {

    private final CredentialsManager credentialsManager;
    private final QuotesTWClient quotesTWClient;
    private final RecipientsService recipientsService;
    private final TWProfilePersistence twProfilePersistence;

    QuotesServiceImpl(CredentialsManager credentialsManager, QuotesTWClient quotesTWClient, RecipientsService recipientsService, TWProfilePersistence twProfilePersistence) {
        this.credentialsManager = credentialsManager;
        this.quotesTWClient = quotesTWClient;
        this.recipientsService = recipientsService;
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
    public Mono<TransferSummary> updateQuote(Long customerId, UUID quoteId, TargetAccount targetAccount) {
        return twProfilePersistence.findByCustomerId(customerId)
                .map(twProfile -> {
                    Mono<Recipient> recipientMono = recipientsService.getRecipient(customerId, Long.valueOf(targetAccount.value()));

                    return credentialsManager.refreshTokens(customerId)
                            .flatMap(twUserTokens -> quotesTWClient.updateQuote(twUserTokens, quoteId, twProfile.getTwProfileId(), targetAccount))
                            .zipWith(recipientMono)
                            .map(quoteRecipientTuple2 -> new TransferSummary(quoteRecipientTuple2.getT1(), quoteRecipientTuple2.getT2()));
                })
                .orElseThrow(ResourceNotFoundException::new);


    }

    @Override
    public Mono<Quote> getQuote(Long customerId, UUID quoteUuid) {
        return credentialsManager.refreshTokens(customerId)
                .flatMap(twUserTokens -> quotesTWClient.getQuote(twUserTokens, quoteUuid));
    }


}
