package com.transferwise.t4b.quote;

import com.transferwise.t4b.credentials.CredentialsManager;
import com.transferwise.t4b.customer.Customer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.transferwise.t4b.client.BodyRequests.forNewQuote;
import static com.transferwise.t4b.client.TransferWisePaths.QUOTES_PATH_V2;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class TransferWiseQuote {

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
}
