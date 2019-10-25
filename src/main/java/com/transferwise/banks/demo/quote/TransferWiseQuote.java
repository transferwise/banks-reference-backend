package com.transferwise.banks.demo.quote;

import com.transferwise.banks.demo.client.params.TargetAccount;
import com.transferwise.banks.demo.credentials.CredentialsManager;
import com.transferwise.banks.demo.customer.Customer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.transferwise.banks.demo.client.BodyRequests.forNewQuote;
import static com.transferwise.banks.demo.client.BodyRequests.forQuoteUpdate;
import static com.transferwise.banks.demo.client.TransferWisePaths.QUOTES_PATH_V2;
import static com.transferwise.banks.demo.client.TransferWisePaths.quotesPathV2;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class TransferWiseQuote {

    private static final MediaType MERGE_PATCH_JSON = MediaType.valueOf("application/merge-patch+json");

    private final WebClient client;
    private final CredentialsManager manager;

    public TransferWiseQuote(final WebClient client, final CredentialsManager manager) {
        this.client = client;
        this.manager = manager;
    }

    public Mono<Quote> create(final Customer customer, final QuoteRequest quoteRequest) {
        return manager.credentialsFor(customer).flatMap(credentials ->
                client.post()
                        .uri(QUOTES_PATH_V2)
                        .header(AUTHORIZATION, credentials.bearer())
                        .contentType(APPLICATION_JSON)
                        .body(forNewQuote(quoteRequest))
                        .retrieve()
                        .bodyToMono(Quote.class));
    }

    public Mono<Quote> createAnonymous(final QuoteRequest quoteRequest) {
        return client.post()
                .uri(QUOTES_PATH_V2)
                .contentType(APPLICATION_JSON)
                .body(forNewQuote(quoteRequest))
                .retrieve()
                .bodyToMono(Quote.class);
    }

    public Mono<Quote> update(final Customer customer, final TargetAccount targetAccount, final UUID quoteId) {
        return manager.credentialsFor(customer).flatMap(credentials ->
                client.patch()
                        .uri(quotesPathV2(quoteId))
                        .header(AUTHORIZATION, credentials.bearer())
                        .contentType(MERGE_PATCH_JSON)
                        .body(forQuoteUpdate(customer.profileId(), targetAccount))
                        .retrieve()
                        .bodyToMono(Quote.class));
    }

    public Mono<Quote> get(final Customer customer, final UUID quoteId) {
        return manager.credentialsFor(customer).flatMap(credentials ->
                client.get()
                        .uri(quotesPathV2(quoteId))
                        .header(AUTHORIZATION, credentials.bearer())
                        .retrieve()
                        .bodyToMono(Quote.class));
    }
}
