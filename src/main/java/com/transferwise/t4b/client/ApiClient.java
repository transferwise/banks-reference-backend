package com.transferwise.t4b.client;

import com.transferwise.t4b.client.params.Code;
import com.transferwise.t4b.credentials.CredentialsManager;
import com.transferwise.t4b.credentials.TransferwiseCredentials;
import com.transferwise.t4b.credentials.TransferwiseProfile;
import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.quote.Quote;
import com.transferwise.t4b.quote.QuoteRequest;
import com.transferwise.t4b.recipient.Recipient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.transferwise.t4b.client.BodyRequests.forCustomerCredentials;
import static com.transferwise.t4b.client.BodyRequests.forNewQuote;
import static com.transferwise.t4b.client.TransferWisePaths.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class ApiClient {

    private final WebClient client;
    private final TransferWiseBankConfig config;
    private final CredentialsManager manager;

    public ApiClient(final WebClient client, final TransferWiseBankConfig config, final CredentialsManager manager) {
        this.client = client;
        this.config = config;
        this.manager = manager;
    }

    public Mono<Customer> attachCredentialsAndProfiles(final Code code, final Customer customer) {
        return createCustomerCredentials(code)
                .map(customer::withCredentials)
                .map(this::profiles)
                .flatMap(Flux::collectList)
                .map(customer::withProfiles);
    }

    private Mono<TransferwiseCredentials> createCustomerCredentials(final Code code) {
        return client.post()
                .uri(OAUTH_TOKEN_PATH)
                .header(AUTHORIZATION, config.basicAuth())
                .body(forCustomerCredentials(config, code))
                .retrieve()
                .bodyToMono(TransferwiseCredentials.class);
    }

    private Flux<TransferwiseProfile> profiles(final Customer customer) {
        return manager.credentialsFor(customer).flatMapMany(credentials ->
                client.get()
                        .uri(PROFILES_PATH_V2)
                        .header(AUTHORIZATION, credentials.bearer())
                        .retrieve()
                        .bodyToFlux(TransferwiseProfile.class));
    }

    public Flux<Recipient> recipients(final Customer customer) {
        return manager.credentialsFor(customer).flatMapMany(credentials ->
                client.get()
                        .uri(new UriWithParams(ACCOUNTS_PATH, customer.profileId()))
                        .header(AUTHORIZATION, credentials.bearer())
                        .retrieve()
                        .bodyToFlux(Recipient.class));
    }

    public Mono<Quote> quote(final Customer customer, final QuoteRequest quoteRequest) {
        return manager.credentialsFor(customer).flatMap(credentials ->
                client.post()
                        .uri(QUOTES_PATH_V2)
                        .header(AUTHORIZATION, credentials.bearer())
                        .contentType(APPLICATION_JSON)
                        .body(forNewQuote(quoteRequest))
                        .retrieve()
                        .bodyToMono(Quote.class));
    }

    public Mono anonymousQuote(final QuoteRequest quoteRequest) {
        return client.post()
                .uri(QUOTES_PATH_V2)
                .contentType(APPLICATION_JSON)
                .body(forNewQuote(quoteRequest))
                .retrieve()
                .bodyToMono(Quote.class);
    }

    public Mono<String> recipientRequirements(final Customer customer) {
        return manager.credentialsFor(customer).flatMap(credentials ->
                client.get()
                        .uri(recipientRequirementsPath(customer.latestQuoteId()))
                        .header(AUTHORIZATION, credentials.bearer())
                        .retrieve()
                        .bodyToMono(String.class));
    }

    public Mono<String> recipientRequirements(final Customer customer, final String bodyRequest) {
        return manager.credentialsFor(customer).flatMap(credentials ->
                client.post()
                        .uri(recipientRequirementsPath(customer.latestQuoteId()))
                        .header(AUTHORIZATION, credentials.bearer())
                        .contentType(APPLICATION_JSON_UTF8)
                        .body(fromObject(bodyRequest))
                        .retrieve()
                        .bodyToMono(String.class));
    }
}
