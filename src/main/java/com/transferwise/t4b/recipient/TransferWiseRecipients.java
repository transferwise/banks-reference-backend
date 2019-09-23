package com.transferwise.t4b.recipient;

import com.transferwise.t4b.client.UriWithParams;
import com.transferwise.t4b.credentials.CredentialsManager;
import com.transferwise.t4b.customer.Customer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.transferwise.t4b.client.TransferWisePaths.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static reactor.core.publisher.Flux.fromIterable;

@Component
public class TransferWiseRecipients {

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

    public Mono<String> requirements(final Customer customer) {
        return manager.credentialsFor(customer).flatMap(credentials ->
                client.get()
                        .uri(recipientRequirementsPath(customer.latestQuoteId()))
                        .header(AUTHORIZATION, credentials.bearer())
                        .retrieve()
                        .bodyToMono(String.class));
    }

    public Mono<String> requirements(final Customer customer, final String bodyRequest) {
        return manager.credentialsFor(customer).flatMap(credentials ->
                client.post()
                        .uri(recipientRequirementsPath(customer.latestQuoteId()))
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
