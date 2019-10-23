package com.transferwise.banks.demo.recipient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transferwise.banks.demo.client.UriWithParams;
import com.transferwise.banks.demo.credentials.CredentialsManager;
import com.transferwise.banks.demo.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.transferwise.banks.demo.client.TransferWisePaths.RECIPIENTS_PATH_V1;
import static com.transferwise.banks.demo.client.TransferWisePaths.RECIPIENTS_PATH_V2;
import static com.transferwise.banks.demo.client.TransferWisePaths.recipientRequirementsPath;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static reactor.core.publisher.Flux.fromIterable;

@Component
public class TransferWiseRecipients {

    private static final Logger log = LoggerFactory.getLogger(TransferWiseRecipients.class);

    private static final String TYPE = "type";
    private static final String EMAIL = "email";

    private final WebClient client;
    private final CredentialsManager manager;

    public TransferWiseRecipients(final WebClient client, final CredentialsManager manager) {
        this.client = client;
        this.manager = manager;
    }

    public Flux<Recipient> all(final Customer customer) {
        return manager.credentialsFor(customer).flatMapMany(credentials ->
                client.get()
                        .uri(new UriWithParams(RECIPIENTS_PATH_V2, customer.profileId()))
                        .header(AUTHORIZATION, credentials.bearer())
                        .retrieve()
                        .bodyToFlux(Content.class)
                        .flatMap(content -> fromIterable(content.recipients)));
    }

    public Mono<List<Map>> requirements(final Customer customer, final UUID quoteId) {
        return manager.credentialsFor(customer).flatMap(credentials ->
                client.get()
                        .uri(recipientRequirementsPath(quoteId))
                        .header(AUTHORIZATION, credentials.bearer())
                        .retrieve()
                        .bodyToMono(String.class))
                .map(json -> {
                    List<Map> accountRequirements = new ArrayList<>();

                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        accountRequirements = mapper.readValue(json, new TypeReference<List<Map>>() {
                        });

                        accountRequirements.removeIf(requirement -> EMAIL.equals(requirement.get(TYPE)));

                    } catch (IOException e) {
                        log.error("Error while parsing account requirements json for quoteId {}", quoteId, e);
                    }

                    return accountRequirements;
                });
    }

    public Mono<String> requirements(final Customer customer, final String bodyRequest, final UUID quoteId) {
        return manager.credentialsFor(customer).flatMap(credentials ->
                client.post()
                        .uri(recipientRequirementsPath(quoteId))
                        .header(AUTHORIZATION, credentials.bearer())
                        .contentType(APPLICATION_JSON_UTF8)
                        .body(fromObject(bodyRequest))
                        .retrieve()
                        .bodyToMono(String.class));
    }

    public Mono<String> create(final Customer customer, final String bodyRequest) {
        return manager.credentialsFor(customer).flatMap(credentials ->
                client.post()
                        .uri(RECIPIENTS_PATH_V1)
                        .header(AUTHORIZATION, credentials.bearer())
                        .contentType(APPLICATION_JSON_UTF8)
                        .body(fromObject(bodyRequest))
                        .retrieve()
                        .bodyToMono(String.class));
    }
}
