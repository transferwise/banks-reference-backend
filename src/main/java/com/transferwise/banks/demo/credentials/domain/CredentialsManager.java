package com.transferwise.banks.demo.credentials.domain;

import com.transferwise.banks.demo.credentials.persistence.twprofile.TWProfilePersistence;
import com.transferwise.banks.demo.credentials.persistence.twuser.TWUserPersistence;
import com.transferwise.banks.demo.credentials.persistence.twusertokens.TWUserTokensPersistence;
import com.transferwise.banks.demo.customer.domain.Customer;
import com.transferwise.banks.demo.customer.domain.CustomersPersistence;
import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class CredentialsManager {

    private final CredentialsTWClient credentialsTWClient;
    private final CustomersPersistence customersPersistence;
    private final TWUserPersistence twUserPersistence;
    private final TWUserTokensPersistence twUserTokensPersistence;
    private final TWProfilePersistence twProfilePersistence;

    public CredentialsManager(final CredentialsTWClient credentialsTWClient,
                              final CustomersPersistence customersPersistence,
                              final TWUserPersistence twUserPersistence,
                              final TWUserTokensPersistence twUserTokensPersistence,
                              final TWProfilePersistence twProfilePersistence) {
        this.credentialsTWClient = credentialsTWClient;
        this.customersPersistence = customersPersistence;
        this.twUserPersistence = twUserPersistence;
        this.twUserTokensPersistence = twUserTokensPersistence;
        this.twProfilePersistence = twProfilePersistence;
    }

    //TODO should this return the userTokens back?
    public void signUp(final Long customerId) {
        String registrationCode = UUID.randomUUID().toString();

        customersPersistence.findById(customerId)
                .ifPresentOrElse(
                        (customer) -> credentialsTWClient.signUp(customer.getEmail(), registrationCode)
                                .map(twUser ->
                                        twUserPersistence.save(twUser
                                                .withCustomerId(customerId)
                                                .withRegistrationCode(registrationCode))
                                )
                                .map(twUser -> credentialsTWClient.getUserTokens(twUser)
                                        .map(twUserTokensPersistence::save)
                                        .map(twUserTokens -> credentialsTWClient.createPersonalProfile(twUserTokens, buildCreatePersonalProfile(customer))
                                                .map(twProfilePersistence::save))),
                        () -> {
                            throw new ResourceNotFoundException();
                        });
    }

    //TODO should this return the userTokens back?
    public void existing(final Long customerId, final String code) {
        customersPersistence.findById(customerId)
                .ifPresentOrElse(
                        (customer) -> credentialsTWClient.getUserTokensForCode(code, customerId)
                                .map(twUserTokensPersistence::save)
                                .map(twUserTokens -> credentialsTWClient.createPersonalProfile(twUserTokens, buildCreatePersonalProfile(customer))),
                        () -> {
                            throw new ResourceNotFoundException();
                        });
    }

    public Mono<TWUserTokens> refreshTokens(final Long customerId) {
        return twUserTokensPersistence.findByCustomerId(customerId)
                .map(twUserTokens -> credentialsTWClient.refresh(twUserTokens)
                        .doOnSuccess(twUserTokensPersistence::save))
                .orElseThrow(ResourceNotFoundException::new);
    }

    private CreatePersonalProfile buildCreatePersonalProfile(Customer customer) {
        CreatePersonalProfileDetails personalProfileDetails = new CreatePersonalProfileDetails(customer.getFirstName(),
                customer.getLastName(),
                customer.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                customer.getPhoneNumber());

        return new CreatePersonalProfile("personal", personalProfileDetails);
    }

    /*public Mono<TransferwiseCredentials> credentialsFor(final CustomerEntity customerId) {
        return refresh(customerEntity.getCredentials())
                .doOnSuccess(c -> customers.save(customerEntity.withCredentials(c)));
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
    }*/
}
