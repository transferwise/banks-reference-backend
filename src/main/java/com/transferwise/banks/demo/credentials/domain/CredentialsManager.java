package com.transferwise.banks.demo.credentials.domain;

import com.transferwise.banks.demo.credentials.domain.twprofile.ProfileService;
import com.transferwise.banks.demo.credentials.persistence.twuser.TWUserPersistence;
import com.transferwise.banks.demo.credentials.persistence.twusertokens.TWUserTokensPersistence;
import com.transferwise.banks.demo.customer.domain.Customer;
import com.transferwise.banks.demo.customer.domain.CustomersPersistence;
import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static java.time.ZonedDateTime.now;

@Component
public class CredentialsManager {

    private final CredentialsTWClient credentialsTWClient;
    private final CustomersPersistence customersPersistence;
    private final TWUserPersistence twUserPersistence;
    private final TWUserTokensPersistence twUserTokensPersistence;
    private final ProfileService profileService;

    public CredentialsManager(final CredentialsTWClient credentialsTWClient,
                              final CustomersPersistence customersPersistence,
                              final TWUserPersistence twUserPersistence,
                              final TWUserTokensPersistence twUserTokensPersistence,
                              final ProfileService profileService) {
        this.credentialsTWClient = credentialsTWClient;
        this.customersPersistence = customersPersistence;
        this.twUserPersistence = twUserPersistence;
        this.twUserTokensPersistence = twUserTokensPersistence;
        this.profileService = profileService;
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
                                profileService.createPersonalProfile(savedTwUserTokens, customer);
                                return savedTwUserTokens;
                            });

                });
    }

    public Mono<TWUserTokens> existing(final Long customerId, final String code) {
        Customer customer = customersPersistence.findById(customerId);

        return credentialsTWClient.getUserTokensForCode(code, customerId)
                .map(twUserTokens -> {
                    TWUserTokens savedTwUserTokens = twUserTokensPersistence.save(twUserTokens);
                    profileService.createPersonalProfile(savedTwUserTokens, customer);
                    return savedTwUserTokens;
                });
    }

    public Mono<TWUserTokens> refreshTokens(final Long customerId) {
        return twUserTokensPersistence.findByCustomerId(customerId)
                .map(this::performRefreshTokens)
                .orElseThrow(ResourceNotFoundException::new)
                .doOnSuccess(twUserTokens -> profileService.performRefreshCycle(twUserTokens, customerId));
    }

    private Mono<TWUserTokens> performRefreshTokens(final TWUserTokens twUserTokens) {
        Mono<TWUserTokens> twUserTokensMono = Mono.just(twUserTokens);

        if (now().isAfter(twUserTokens.getExpiryTime())) {
            twUserTokensMono = credentialsTWClient.refresh(twUserTokens)
                    .map(twUserTokensPersistence::save);
        }

        return twUserTokensMono;
    }

}
