package com.transferwise.banks.demo.recipient.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import com.transferwise.banks.demo.credentials.persistence.twprofile.TWProfilePersistence;
import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
class RecipientsServiceImpl implements RecipientsService {

    private static final Logger log = LoggerFactory.getLogger(RecipientsServiceImpl.class);

    private static final String TYPE = "type";
    private static final String EMAIL = "email";


    private final CredentialsManager credentialsManager;
    private final RecipientsTWClient recipientsTWClient;
    private final TWProfilePersistence twProfilePersistence;
    private final ObjectMapper objectMapper;

    RecipientsServiceImpl(CredentialsManager credentialsManager, RecipientsTWClient recipientsTWClient, TWProfilePersistence twProfilePersistence, ObjectMapper objectMapper) {
        this.credentialsManager = credentialsManager;
        this.recipientsTWClient = recipientsTWClient;
        this.twProfilePersistence = twProfilePersistence;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Recipient> getRecipient(final Long customerId, final Long recipientId) {
        return credentialsManager.refreshTokens(customerId)
                .flatMap(twUserTokens -> recipientsTWClient.getRecipient(twUserTokens, recipientId));

    }

    @Override
    public Flux<Recipient> getAllRecipients(Long customerId) {
        return twProfilePersistence.findByCustomerId(customerId)
                .map(twProfile -> credentialsManager.refreshTokens(customerId)
                        .flatMapMany(twUserTokens -> recipientsTWClient.getAllRecipients(twUserTokens, twProfile.getTwProfileId())))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Mono<String> create(Long customerId, String requestBody) {
        return credentialsManager.refreshTokens(customerId)
                .flatMap(twUserTokens -> recipientsTWClient.create(twUserTokens, requestBody));

    }

    @Override
    public Mono<List<Map>> getRequirements(Long customerId, UUID quoteId) {
        return credentialsManager.refreshTokens(customerId)
                .flatMap(twUserTokens ->
                        recipientsTWClient.getRequirements(twUserTokens, quoteId)
                                .map(json -> {
                                    List<Map> accountRequirements = new ArrayList<>();

                                    try {
                                        accountRequirements = objectMapper.readValue(json, new TypeReference<List<Map>>() {
                                        });

                                        accountRequirements.removeIf(requirement -> EMAIL.equals(requirement.get(TYPE)));

                                    } catch (IOException e) {
                                        log.error("Error while parsing account requirements json for quoteId {}", quoteId, e);
                                    }

                                    return accountRequirements;
                                })
                );
    }

    @Override
    public Mono<String> createRequirements(Long customerId, UUID quoteId, String requestBody) {
        return credentialsManager.refreshTokens(customerId)
                .flatMap(twUserTokens -> recipientsTWClient.createRequirements(twUserTokens, quoteId, requestBody));
    }

}
