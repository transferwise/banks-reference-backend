package com.transferwise.banks.demo.quote;

import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

//@Component
public class TransferWiseQuote {

    private static final MediaType MERGE_PATCH_JSON = MediaType.valueOf("application/merge-patch+json");

    private final WebClient client;
    private final CredentialsManager manager;

    public TransferWiseQuote(final WebClient client, final CredentialsManager manager) {
        this.client = client;
        this.manager = manager;
    }

    /*public Mono<Quote> create(final CustomerEntity customerEntity, final QuoteRequest quoteRequest) {
        return manager.credentialsFor(customerEntity).flatMap(credentials ->
                client.post()
                        .uri(QUOTES_PATH_V2)
                        .header(AUTHORIZATION, credentials.bearer())
                        .contentType(APPLICATION_JSON)
                        .body(forNewQuote(quoteRequest))
                        .retrieve()
                        .bodyToMono(Quote.class));
    }*/

    /*public Mono<Quote> createAnonymous(final QuoteRequest quoteRequest) {
        return client.post()
                .uri(QUOTES_PATH_V2)
                .contentType(APPLICATION_JSON)
                .body(forNewQuote(quoteRequest))
                .retrieve()
                .bodyToMono(Quote.class);
    }*/

    /*public Mono<Quote> update(final CustomerEntity customerEntity, final TargetAccount targetAccount, final UUID quoteId) {
        return manager.credentialsFor(customerEntity).flatMap(credentials ->
                client.patch()
                        .uri(quotesPathV2(quoteId))
                        .header(AUTHORIZATION, credentials.bearer())
                        .contentType(MERGE_PATCH_JSON)
                        .body(forQuoteUpdate(customerEntity.profileId(), targetAccount))
                        .retrieve()
                        .bodyToMono(Quote.class));
    }*/

   /* public Mono<Quote> get(final Customer customer, final UUID quoteId) {
        return manager.credentialsFor(customer).flatMap(credentials ->
                client.get()
                        .uri(quotesPathV2(quoteId))
                        .header(AUTHORIZATION, credentials.bearer())
                        .retrieve()
                        .bodyToMono(Quote.class));
    }*/
}
