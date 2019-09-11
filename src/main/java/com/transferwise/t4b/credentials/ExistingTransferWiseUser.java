package com.transferwise.t4b.credentials;

import com.transferwise.t4b.client.TransferWiseBankConfig;
import com.transferwise.t4b.client.params.Code;
import com.transferwise.t4b.customer.Customer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.transferwise.t4b.client.BodyRequests.forCustomerCredentials;
import static com.transferwise.t4b.client.TransferWisePaths.OAUTH_TOKEN_PATH;
import static com.transferwise.t4b.client.TransferWisePaths.PROFILES_PATH_V2;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class ExistingTransferWiseUser {

    private final WebClient client;
    private final TransferWiseBankConfig config;
    private final CredentialsManager manager;

    public ExistingTransferWiseUser(final WebClient client, final TransferWiseBankConfig config, final CredentialsManager manager) {
        this.client = client;
        this.config = config;
        this.manager = manager;
    }

    public Mono<Customer> attach(final Code code, final Customer customer) {
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
}
