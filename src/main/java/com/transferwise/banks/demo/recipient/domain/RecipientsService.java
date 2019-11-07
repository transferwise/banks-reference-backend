package com.transferwise.banks.demo.recipient.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface RecipientsService {

    Mono<Recipient> getRecipient(Long customerId, Long recipientId);

    Flux<Recipient> getAllRecipients(Long customerId);

    Mono<String> create(Long customerId, String requestBody);

    Mono<List<Map>> getRequirements(Long customerId, UUID quoteId);

    Mono<String> createRequirements(Long customerId, UUID quoteId, String requestBody);
}
