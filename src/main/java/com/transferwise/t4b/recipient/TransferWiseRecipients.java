package com.transferwise.t4b.recipient;

import com.transferwise.t4b.client.UriWithParams;
import com.transferwise.t4b.credentials.CredentialsManager;
import com.transferwise.t4b.customer.Customer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static com.transferwise.t4b.client.TransferWisePaths.ACCOUNTS_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

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
                        .uri(new UriWithParams(ACCOUNTS_PATH, customer.profileId()))
                        .header(AUTHORIZATION, credentials.bearer())
                        .retrieve()
                        .bodyToFlux(Recipient.class));
    }
}
