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

import static java.time.ZonedDateTime.now;

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

    public Mono<TWUserTokens> signUp(final Long customerId) {
        String registrationCode = UUID.randomUUID().toString();

        Customer customer = customersPersistence.findById(customerId);

        return credentialsTWClient.signUp(customer.getEmail(), registrationCode)
                .flatMap(twUser -> {
                    TWUser savedTwUser = twUserPersistence.save(twUser
                            .withCustomerId(customerId)
                            .withRegistrationCode(registrationCode));
                    return credentialsTWClient.getUserTokens(savedTwUser)
                            .map(twUserTokens -> {
                                TWUserTokens savedTwUserTokens = twUserTokensPersistence.save(twUserTokens);
                                credentialsTWClient.createPersonalProfile(savedTwUserTokens, buildCreatePersonalProfile(customer))
                                        .doOnSuccess(twProfilePersistence::save);
                                return savedTwUserTokens;
                            });

                });
    }

    public Mono<TWUserTokens> existing(final Long customerId, final String code) {
        Customer customer = customersPersistence.findById(customerId);

        return credentialsTWClient.getUserTokensForCode(code, customerId)
                .map(twUserTokens -> {
                    TWUserTokens savedTwUserTokens = twUserTokensPersistence.save(twUserTokens);
                    credentialsTWClient.createPersonalProfile(savedTwUserTokens, buildCreatePersonalProfile(customer));
                    return savedTwUserTokens;
                });
    }

    public Mono<TWUserTokens> refreshTokens(final Long customerId) {
        return twUserTokensPersistence.findByCustomerId(customerId)
                .map(twUserTokens -> {
                    if (now().isAfter(twUserTokens.getExpiryTime())) {
                        return credentialsTWClient.refresh(twUserTokens)
                                .doOnSuccess(twUserTokensPersistence::save);
                    } else {
                        return Mono.just(twUserTokens);
                    }
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    private CreatePersonalProfile buildCreatePersonalProfile(Customer customer) {
        CreatePersonalProfileDetails personalProfileDetails = new CreatePersonalProfileDetails(customer.getFirstName(),
                customer.getLastName(),
                customer.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                customer.getPhoneNumber());

        return new CreatePersonalProfile("personal", personalProfileDetails);
    }
}
