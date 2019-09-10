package com.transferwise.t4b.credentials;

import com.transferwise.t4b.client.Authorizations;
import com.transferwise.t4b.client.TransferwiseClientCredentials;
import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.CustomersRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static com.transferwise.t4b.client.BodyRequests.forClientCredentials;
import static com.transferwise.t4b.client.BodyRequests.forRefreshToken;
import static com.transferwise.t4b.client.TransferWisePaths.BASE_URL;
import static com.transferwise.t4b.client.TransferWisePaths.OAUTH_TOKEN_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Component
public class CredentialsManager {

    private final CustomersRepository customers;
    private final Authorizations auth;
    private final WebClient client;

    public CredentialsManager(final CustomersRepository customers, final Authorizations auth) {
        this.customers = customers;
        this.auth = auth;
        client = WebClient.builder().baseUrl(BASE_URL).build();
    }

    public Mono<TransferwiseCredentials> credentialsFor(final Customer customer) {
        return refresh(customer.getCredentials())
                .doOnSuccess(c -> customers.save(customer.withCredentials(c)));
    }

    private Mono<TransferwiseCredentials> refresh(final TransferwiseCredentials credentials) {
        if (credentials.areExpired()) {
            return client.post()
                    .uri(OAUTH_TOKEN_PATH)
                    .header(AUTHORIZATION, auth.basic())
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
                .header(AUTHORIZATION, auth.basic())
                .body(forClientCredentials())
                .retrieve()
                .bodyToMono(TransferwiseClientCredentials.class);
    }
}
