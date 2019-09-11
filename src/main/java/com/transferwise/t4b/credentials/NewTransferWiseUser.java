package com.transferwise.t4b.credentials;

import com.transferwise.t4b.client.TransferWiseBankConfig;
import com.transferwise.t4b.client.TransferwiseClientCredentials;
import com.transferwise.t4b.client.params.RegistrationCode;
import com.transferwise.t4b.customer.Customer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.transferwise.t4b.client.BodyRequests.*;
import static com.transferwise.t4b.client.TransferWisePaths.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class NewTransferWiseUser {

    private final WebClient client;
    private final TransferWiseBankConfig config;
    private final CredentialsManager manager;

    public NewTransferWiseUser(final WebClient client, final TransferWiseBankConfig config, final CredentialsManager manager) {
        this.client = client;
        this.config = config;
        this.manager = manager;
    }

    public Mono<Customer> create(final Customer customer) {
        final var registrationCode = new RegistrationCode();

        return manager.withBankCredentials(credentials ->
                createUser(customer, credentials, registrationCode)
                        .map(user -> user.withRegistrationCode(registrationCode))
                        .map(customer::withUser)
                        .flatMap(c -> createUserCredentials(c.getUser()))
                        .map(customer::withCredentials)
                        .flatMap(this::createPersonalProfile)
                        .map(customer::withPersonalProfile));
    }

    private Mono<TransferwiseUser> createUser(final Customer customer,
                                              final TransferwiseClientCredentials credentials,
                                              final RegistrationCode registrationCode) {
        return client.post()
                .uri(SIGNUP_PATH)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, credentials.bearer())
                .body(forNewUser(customer.email(), registrationCode.v1()))
                .retrieve()
                .bodyToMono(TransferwiseUser.class);
    }

    private Mono<TransferwiseCredentials> createUserCredentials(final TransferwiseUser user) {
        return client.post()
                .uri(OAUTH_TOKEN_PATH)
                .contentType(APPLICATION_FORM_URLENCODED)
                .header(AUTHORIZATION, config.basicAuth())
                .body(forUserCredentials(config, user))
                .retrieve()
                .bodyToMono(TransferwiseCredentials.class);
    }

    private Mono<TransferwiseProfile> createPersonalProfile(final Customer customer) {
        return manager.credentialsFor(customer).flatMap(credentials ->
                client.post()
                        .uri(PROFILES_PATH_V1)
                        .contentType(APPLICATION_JSON)
                        .header(AUTHORIZATION, credentials.bearer())
                        .body(forPersonalProfile(customer))
                        .retrieve()
                        .bodyToMono(TransferwiseProfile.class));
    }
}