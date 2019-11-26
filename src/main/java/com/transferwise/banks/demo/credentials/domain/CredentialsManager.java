package com.transferwise.banks.demo.credentials.domain;

import com.transferwise.banks.demo.credentials.domain.twprofile.ProfileService;
import com.transferwise.banks.demo.credentials.domain.twprofile.TWProfile;
import com.transferwise.banks.demo.credentials.persistence.twprofile.TWProfilePersistence;
import com.transferwise.banks.demo.credentials.persistence.twuser.TWUserPersistence;
import com.transferwise.banks.demo.credentials.persistence.twusertokens.TWUserTokensPersistence;
import com.transferwise.banks.demo.customer.domain.Customer;
import com.transferwise.banks.demo.customer.domain.CustomersPersistence;
import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.ZonedDateTime.now;

@Component
public class CredentialsManager {

    private final CredentialsTWClient credentialsTWClient;
    private final CustomersPersistence customersPersistence;
    private final TWUserPersistence twUserPersistence;
    private final TWUserTokensPersistence twUserTokensPersistence;
    private final ProfileService profileService;
    private final TWProfilePersistence twProfilePersistence;


    public CredentialsManager(final CredentialsTWClient credentialsTWClient,
                              final CustomersPersistence customersPersistence,
                              final TWUserPersistence twUserPersistence,
                              final TWUserTokensPersistence twUserTokensPersistence,
                              final ProfileService profileService,
                              final TWProfilePersistence twProfilePersistence) {
        this.credentialsTWClient = credentialsTWClient;
        this.customersPersistence = customersPersistence;
        this.twUserPersistence = twUserPersistence;
        this.twUserTokensPersistence = twUserTokensPersistence;
        this.profileService = profileService;
        this.twProfilePersistence = twProfilePersistence;
    }

    public Mono<TWProfile> signUp(final Long customerId) {
        String registrationCode = UUID.randomUUID().toString();

        Customer customer = customersPersistence.findById(customerId);

        return credentialsTWClient.signUp(customer.getEmail(), registrationCode)
                .map(twUser -> twUserPersistence.save(twUser
                        .withCustomerId(customerId)
                        .withRegistrationCode(registrationCode)))
                .flatMap(credentialsTWClient::getUserTokens)
                .map(twUserTokensPersistence::save)
                .flatMap(savedTwUserTokens -> profileService.createPersonalProfile(savedTwUserTokens, customer))
                .map(twProfile -> twProfilePersistence.save(twProfile.withUpdatedAt(LocalDateTime.now())));
    }

    public Mono<TWProfile> existing(final Long customerId, final String code) {
        Customer customer = customersPersistence.findById(customerId);


        Mono<TWUserTokens> savedTwUserTokens = credentialsTWClient.getUserTokensForCode(code, customerId)
                .map(twUserTokensPersistence::save);

        return savedTwUserTokens.flatMap(profileService::getPersonalProfile)
                .switchIfEmpty(savedTwUserTokens.flatMap(tokens -> profileService.createPersonalProfile(tokens, customer)))
                .map(twProfile -> twProfilePersistence.save(twProfile.withUpdatedAt(LocalDateTime.now())));
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
