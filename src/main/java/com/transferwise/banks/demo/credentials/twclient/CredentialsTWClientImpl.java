package com.transferwise.banks.demo.credentials.twclient;

import com.transferwise.banks.demo.client.params.ClientId;
import com.transferwise.banks.demo.client.params.Code;
import com.transferwise.banks.demo.client.params.Email;
import com.transferwise.banks.demo.client.params.GrantTypeAuthorizationCode;
import com.transferwise.banks.demo.client.params.GrantTypeRefreshToken;
import com.transferwise.banks.demo.client.params.GrantTypeRegistrationCode;
import com.transferwise.banks.demo.client.params.Parameter;
import com.transferwise.banks.demo.client.params.RedirectUri;
import com.transferwise.banks.demo.client.params.RefreshToken;
import com.transferwise.banks.demo.client.params.RegistrationCode;
import com.transferwise.banks.demo.credentials.domain.CreatePersonalProfile;
import com.transferwise.banks.demo.credentials.domain.CredentialsTWClient;
import com.transferwise.banks.demo.credentials.domain.TWProfile;
import com.transferwise.banks.demo.credentials.domain.TWUser;
import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Map;

import static com.transferwise.banks.demo.client.TransferWisePaths.OAUTH_TOKEN_PATH;
import static com.transferwise.banks.demo.client.TransferWisePaths.PROFILES_PATH_V1;
import static com.transferwise.banks.demo.client.TransferWisePaths.SIGNUP_PATH;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.ZonedDateTime.now;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromMultipartData;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
class CredentialsTWClientImpl implements CredentialsTWClient {

    private static final String GRANT_TYPE_CLIENT_CREDENTIALS = "grant_type=client_credentials";

    @Value("${twbank.clientId}") //TODO maybe rename properties?
    private String clientId;

    @Value("${twbank.secret}") //TODO maybe rename properties?
    private String secret;

    @Value("${twbank.redirectUri}") //TODO maybe rename properties?
    private String redirectUri;

    private final WebClient client;

    CredentialsTWClientImpl(WebClient client) {
        this.client = client;
    }

    @Override
    public Mono<TWUser> signUp(final String email,
                               final String registrationCode) {

        return twClientCredentials()
                .flatMap(twClientCredentials -> signUp(email, registrationCode, twClientCredentials));
    }

    @Override
    public Mono<TWUserTokens> getUserTokensForCode(final String code, final Long customerId) {
        return client.post()
                .uri(OAUTH_TOKEN_PATH)
                .header(AUTHORIZATION, basicAuth())
                .body(forAuthorizationCode(code))
                .retrieve()
                .bodyToMono(TWUserTokensResponse.class)
                .map(twUserTokensResponse -> new TWUserTokens(customerId,
                        null,
                        twUserTokensResponse.getAccessToken(),
                        twUserTokensResponse.getRefreshToken(),
                        now().plusSeconds(twUserTokensResponse.getExpiresIn())));
    }

    @Override
    public Mono<TWUserTokens> getUserTokens(final TWUser twUser) {
        return client.post()
                .uri(OAUTH_TOKEN_PATH)
                .contentType(APPLICATION_FORM_URLENCODED)
                .header(AUTHORIZATION, basicAuth())
                .body(forUserCredentials(twUser))
                .retrieve()
                .bodyToMono(TWUserTokensResponse.class)
                .map(twUserTokensResponse -> new TWUserTokens(twUser.getCustomerId(),
                        twUser.getTwUserId(),
                        twUserTokensResponse.getAccessToken(),
                        twUserTokensResponse.getRefreshToken(),
                        now().plusSeconds(twUserTokensResponse.getExpiresIn())));
    }

    @Override
    public Mono<TWProfile> createPersonalProfile(final TWUserTokens twUserTokens, final CreatePersonalProfile createPersonalProfile) {
        return client.post()
                .uri(PROFILES_PATH_V1)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, twUserTokens.bearer())
                .body(fromObject(createPersonalProfile))
                .retrieve()
                .bodyToMono(TWProfileResponse.class)
                .map(twProfileResponse -> new TWProfile(twProfileResponse.getId(),
                        twUserTokens.getCustomerId(),
                        twProfileResponse.getType()));
    }


    @Override
    public Mono<TWUserTokens> refresh(final TWUserTokens twUserTokens) {
        if (now().isAfter(twUserTokens.getExpiryTime())) {
            return client.post()
                    .uri(OAUTH_TOKEN_PATH)
                    .header(AUTHORIZATION, basicAuth())
                    .body(forRefreshToken(new RefreshToken(twUserTokens.getRefreshToken())))
                    .retrieve()
                    .bodyToMono(TWUserTokens.class);
        }

        return Mono.just(twUserTokens);
    }

    private Mono<TWClientCredentials> twClientCredentials() {
        return client.post()
                .uri(OAUTH_TOKEN_PATH)
                .contentType(APPLICATION_FORM_URLENCODED)
                .header(AUTHORIZATION, basicAuth())
                .body(fromObject(GRANT_TYPE_CLIENT_CREDENTIALS))
                .retrieve()
                .bodyToMono(TWClientCredentials.class);
    }

    private Mono<TWUser> signUp(String email, String registrationCode, TWClientCredentials twClientCredentials) {
        return client.post()
                .uri(SIGNUP_PATH)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, twClientCredentials.bearer())
                .body(fromObject(new TWSignUpRequest(email, registrationCode)))
                .retrieve()
                .bodyToMono(TWUser.class);
    }


    private String basicAuth() {
        return String.format("Basic %s", encodedCredentials());
    }

    private String encodedCredentials() {
        return Base64Utils.encodeToString(credentials().getBytes(UTF_8));
    }

    private String credentials() {
        return clientId + ":" + secret;
    }


    private BodyInserters.MultipartInserter forUserCredentials(final TWUser twUser) {
        return fromMultipartData(
                multiMap(new GrantTypeRegistrationCode(),
                        new Email(twUser.getEmail()),
                        new ClientId(clientId),
                        new RegistrationCode(twUser.getRegistrationCode())));
    }

    private BodyInserters.MultipartInserter forAuthorizationCode(final String code) {
        return fromMultipartData(
                multiMap(new GrantTypeAuthorizationCode(),
                        new ClientId(clientId),
                        new Code(code),
                        new RedirectUri(redirectUri)));
    }

    //TODO check if it makes sense to keep these in a BodyRequests class or if we should rewrite it
    private static Map<String, String> map(final Parameter... parameters) {
        return Arrays.stream(parameters).collect(toMap(Parameter::key, Parameter::value));
    }

    private static MultiValueMap<String, String> multiMap(final Parameter... parameters) {
        final var multiMap = new LinkedMultiValueMap<String, String>();
        multiMap.setAll(map(parameters));
        return multiMap;
    }

    private static BodyInserters.MultipartInserter forRefreshToken(final RefreshToken refreshToken) {
        return fromMultipartData(multiMap(new GrantTypeRefreshToken(), refreshToken));
    }


}
