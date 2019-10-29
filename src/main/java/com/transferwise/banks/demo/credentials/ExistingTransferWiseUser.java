package com.transferwise.banks.demo.credentials;

//@Component
public class ExistingTransferWiseUser {
/*
    private final WebClient client;
    private final TransferWiseBankConfig config;
    private final CredentialsManager manager;

    public ExistingTransferWiseUser(final WebClient client, final TransferWiseBankConfig config, final CredentialsManager manager) {
        this.client = client;
        this.config = config;
        this.manager = manager;
    }

    public Mono<CustomerEntity> attach(final Code code, final CustomerEntity customerEntity) {
        return createCustomerCredentials(code)
                .map(customerEntity::withCredentials)
                .map(this::profiles)
                .flatMap(Flux::collectList)
                .map(customerEntity::withProfiles);
    }

    private Mono<TransferwiseCredentials> createCustomerCredentials(final Code code) {
        return client.post()
                .uri(OAUTH_TOKEN_PATH)
                .header(AUTHORIZATION, config.basicAuth())
                .body(forCustomerCredentials(config, code))
                .retrieve()
                .bodyToMono(TransferwiseCredentials.class);
    }

    private Flux<TransferwiseProfile> profiles(final CustomerEntity customerEntity) {
        return manager.credentialsFor(customerEntity).flatMapMany(credentials ->
                client.get()
                        .uri(PROFILES_PATH_V2)
                        .header(AUTHORIZATION, credentials.bearer())
                        .retrieve()
                        .bodyToFlux(TransferwiseProfile.class));
    }*/
}
