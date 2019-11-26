package com.transferwise.banks.demo.credentials.domain.twprofile;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProfileTWClient {

    Mono<TWProfile> createPersonalProfile(TWUserTokens twUserTokens, CreatePersonalProfile createPersonalProfile);

    Mono<TWProfile> updatePersonalProfile(TWUserTokens twUserTokens, UpdatePersonalProfile updatePersonalProfile);

    Mono<TWProfile> getProfile(TWUserTokens twUserTokens, Long twProfileId);

    Flux<TWProfile> getProfiles(TWUserTokens twUserTokens);

    Mono<Boolean> openUpdateWindow(TWUserTokens twUserTokens, Long twProfileId);

    Mono<Boolean> closeUpdateWindow(TWUserTokens twUserTokens, Long twProfileId);
}
