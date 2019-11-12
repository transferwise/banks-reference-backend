package com.transferwise.banks.demo.credentials.domain;

import reactor.core.publisher.Mono;

public interface CredentialsTWClient {

    Mono<TWUser> signUp(String email, String registrationCode);

    Mono<TWUserTokens> getUserTokensForCode(String code, Long customerId);

    Mono<TWUserTokens> getUserTokens(TWUser twUser);

    Mono<TWUserTokens> refresh(TWUserTokens twUserTokens);
}
