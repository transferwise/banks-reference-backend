package com.transferwise.banks.demo.credentials.twclient.twprofile;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.credentials.domain.twprofile.CreatePersonalProfile;
import com.transferwise.banks.demo.credentials.domain.twprofile.ProfileDetails;
import com.transferwise.banks.demo.credentials.domain.twprofile.ProfileTWClient;
import com.transferwise.banks.demo.credentials.domain.twprofile.TWProfile;
import com.transferwise.banks.demo.credentials.domain.twprofile.UpdatePersonalProfile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.transferwise.banks.demo.client.TransferWisePaths.PROFILES_PATH_V1;
import static com.transferwise.banks.demo.client.TransferWisePaths.getProfilePath;
import static com.transferwise.banks.demo.client.TransferWisePaths.updateWindowPath;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
class ProfileTWClientImpl implements ProfileTWClient {

    private final WebClient client;

    public ProfileTWClientImpl(WebClient client) {
        this.client = client;
    }

    @Override
    public Mono<TWProfile> createPersonalProfile(TWUserTokens twUserTokens, CreatePersonalProfile createPersonalProfile) {
        return client.post()
                .uri(PROFILES_PATH_V1)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, twUserTokens.bearer())
                .body(fromObject(createPersonalProfile))
                .retrieve()
                .bodyToMono(TWProfileResponse.class)
                .map(twProfileResponse -> new TWProfile(twProfileResponse.getId(),
                        twUserTokens.getCustomerId(),
                        twProfileResponse.getType(),
                        mapToProfileDetails(twProfileResponse.getDetails())));

    }

    @Override
    public Mono<TWProfile> updatePersonalProfile(TWUserTokens twUserTokens, UpdatePersonalProfile updatePersonalProfile) {
        return client.put()
                .uri(PROFILES_PATH_V1)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, twUserTokens.bearer())
                .body(fromObject(updatePersonalProfile))
                .retrieve()
                .bodyToMono(TWProfileResponse.class)
                .map(twProfileResponse -> new TWProfile(twProfileResponse.getId(),
                        twUserTokens.getCustomerId(),
                        twProfileResponse.getType(),
                        mapToProfileDetails(twProfileResponse.getDetails())));
    }

    @Override
    public Mono<TWProfile> getProfile(final TWUserTokens twUserTokens, final Long twProfileId) {
        return client.get()
                .uri(getProfilePath(twProfileId))
                .header(AUTHORIZATION, twUserTokens.bearer())
                .retrieve()
                .bodyToMono(TWProfileResponse.class)
                .map(twProfileResponse -> new TWProfile(twProfileResponse.getId(),
                        twUserTokens.getCustomerId(),
                        twProfileResponse.getType(),
                        mapToProfileDetails(twProfileResponse.getDetails())));
    }

    @Override
    public Flux<TWProfile> getProfiles(final TWUserTokens twUserTokens) {
        return client.get()
                .uri(PROFILES_PATH_V1)
                .header(AUTHORIZATION, twUserTokens.bearer())
                .retrieve()
                .bodyToFlux(TWProfileResponse.class)
                .map(twProfileResponse -> new TWProfile(twProfileResponse.getId(),
                        twUserTokens.getCustomerId(),
                        twProfileResponse.getType(),
                        mapToProfileDetails(twProfileResponse.getDetails())));
    }

    @Override
    public Mono<Boolean> openUpdateWindow(TWUserTokens twUserTokens, Long twProfileId) {
        return client.post()
                .uri(updateWindowPath(twProfileId))
                .header(AUTHORIZATION, twUserTokens.bearer())
                .exchange()
                .map(clientResponse -> clientResponse.statusCode().is2xxSuccessful());
    }

    @Override
    public Mono<Boolean> closeUpdateWindow(TWUserTokens twUserTokens, Long twProfileId) {
        return client.delete()
                .uri(updateWindowPath(twProfileId))
                .header(AUTHORIZATION, twUserTokens.bearer())
                .exchange()
                .map(clientResponse -> clientResponse.statusCode().is2xxSuccessful());
    }

    private ProfileDetails mapToProfileDetails(TWProfileDetails twProfileDetails) {
        if (twProfileDetails != null) {
            return new ProfileDetails(twProfileDetails.getFirstName(),
                    twProfileDetails.getLastName(),
                    twProfileDetails.getDateOfBirth(),
                    twProfileDetails.getPhoneNumber());
        }
        return null;
    }
}
