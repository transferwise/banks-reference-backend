package com.transferwise.banks.demo.credentials;

import com.transferwise.banks.demo.client.TransferWiseBankConfig;
import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import org.springframework.web.reactive.function.client.WebClient;

//@Component
public class NewTransferWiseUser {

    private final WebClient client;
    private final TransferWiseBankConfig config;
    private final CredentialsManager manager;

    public NewTransferWiseUser(final WebClient client, final TransferWiseBankConfig config, final CredentialsManager manager) {
        this.client = client;
        this.config = config;
        this.manager = manager;
    }

   /* public Mono<CustomerEntity> create(final CustomerEntity customerEntity) {
        final var registrationCode = new RegistrationCode();

        return manager.withBankCredentials(credentials ->
                createUser(customerEntity, credentials, registrationCode)
                        .map(user -> user.withRegistrationCode(registrationCode))
                        .map(customerEntity::withUser)
                        .flatMap(c -> createUserCredentials(c.getUser()))
                        .map(customerEntity::withCredentials)
                        .flatMap(this::createPersonalProfile)
                        .map(customerEntity::withPersonalProfile));
    }

    private Mono<TransferwiseUser> createUser(final CustomerEntity customerEntity,
                                              final TransferwiseClientCredentials credentials,
                                              final RegistrationCode registrationCode) {
        return client.post()
                .uri(SIGNUP_PATH)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, credentials.bearer())
                .body(forNewUser(customerEntity.email(), registrationCode.v1()))
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

    private Mono<TransferwiseProfile> createPersonalProfile(final CustomerEntity customerEntity) {
        return manager.credentialsFor(customerEntity).flatMap(credentials ->
                client.post()
                        .uri(PROFILES_PATH_V1)
                        .contentType(APPLICATION_JSON)
                        .header(AUTHORIZATION, credentials.bearer())
                        .body(forPersonalProfile(customerEntity))
                        .retrieve()
                        .bodyToMono(TransferwiseProfile.class));
    }*/
}