package com.transferwise.t4b.client;

import com.transferwise.t4b.credentials.CredentialsManager;
import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.recipient.Recipient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static com.transferwise.t4b.client.TransferWisePaths.ACCOUNTS_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

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

    public Flux<Recipient> recipients(final Customer customer) {
        return manager.credentialsFor(customer).flatMapMany(credentials ->
                client.get()
                        .uri(new UriWithParams(ACCOUNTS_PATH, customer.profileId()))
                        .header(AUTHORIZATION, credentials.bearer())
                        .retrieve()
                        .bodyToFlux(Recipient.class));
    }
}
