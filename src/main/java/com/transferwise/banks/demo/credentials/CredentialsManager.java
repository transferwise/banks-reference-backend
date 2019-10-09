package com.transferwise.banks.demo.credentials;

import com.transferwise.banks.demo.client.TransferWiseBankConfig;
import com.transferwise.banks.demo.client.TransferwiseClientCredentials;
import com.transferwise.banks.demo.customer.Customer;
import com.transferwise.banks.demo.customer.CustomersRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static com.transferwise.banks.demo.client.BodyRequests.forClientCredentials;
import static com.transferwise.banks.demo.client.BodyRequests.forRefreshToken;
import static com.transferwise.banks.demo.client.TransferWisePaths.OAUTH_TOKEN_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Component
public class CredentialsManager {

    private final WebClient client;
    private final TransferWiseBankConfig config;
    private final CustomersRepository customers;

    public CredentialsManager(final WebClient client, final TransferWiseBankConfig config, final CustomersRepository customers) {
        this.client = client;
        this.config = config;
        this.customers = customers;
    }

    public Mono<TransferwiseCredentials> credentialsFor(final Customer customer) {
        return refresh(customer.getCredentials())
                .doOnSuccess(c -> customers.save(customer.withCredentials(c)));
    }

    private Mono<TransferwiseCredentials> refresh(final TransferwiseCredentials credentials) {
        if (credentials.areExpired()) {
            return client.post()
                    .uri(OAUTH_TOKEN_PATH)
                    .header(AUTHORIZATION, config.basicAuth())
                    .body(forRefreshToken(credentials.refreshToken()))
                    .retrieve()
                    .bodyToMono(TransferwiseCredentials.class);
        }

        return Mono.just(credentials);
    }

    public <T> Mono<T> withBankCredentials(final Function<TransferwiseClientCredentials, Mono<T>> f) {
        return bankCredentials().flatMap(f);
    }

    private Mono<TransferwiseClientCredentials> bankCredentials() {
        return client.post()
                .uri(OAUTH_TOKEN_PATH)
                .contentType(APPLICATION_FORM_URLENCODED)
                .header(AUTHORIZATION, config.basicAuth())
                .body(forClientCredentials())
                .retrieve()
                .bodyToMono(TransferwiseClientCredentials.class);
    }
}
