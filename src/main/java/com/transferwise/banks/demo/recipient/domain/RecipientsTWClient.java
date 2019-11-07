package com.transferwise.banks.demo.recipient.domain;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RecipientsTWClient {

    Mono<Recipient> getRecipient(TWUserTokens twUserTokens, Long recipientId);

    Flux<Recipient> getAllRecipients(TWUserTokens twUserTokens, Long twProfileId);

    Mono<String> create(TWUserTokens twUserTokens, String requestBody);

    Mono<String> getRequirements(TWUserTokens twUserTokens, UUID quoteId);

    Mono<String> createRequirements(TWUserTokens twUserTokens, UUID quoteId, String requestBody);
}
