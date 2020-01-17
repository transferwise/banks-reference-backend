package com.transferwise.banks.demo.credentials.twclient;

import com.transferwise.banks.demo.client.params.RefreshToken;
import com.transferwise.banks.demo.credentials.domain.CredentialsTWClient;
import com.transferwise.banks.demo.credentials.domain.TWUser;
import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.transferwise.banks.demo.client.BodyRequests.forAuthorizationCode;
import static com.transferwise.banks.demo.client.BodyRequests.forRefreshToken;
import static com.transferwise.banks.demo.client.BodyRequests.forUserCredentials;
import static com.transferwise.banks.demo.client.TransferWisePaths.OAUTH_TOKEN_PATH;
import static com.transferwise.banks.demo.client.TransferWisePaths.SIGNUP_PATH;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.ZonedDateTime.now;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
class CredentialsTWClientImpl implements CredentialsTWClient {

    private static final String GRANT_TYPE_CLIENT_CREDENTIALS = "grant_type=client_credentials";

    @Value("${twbank.clientId}")
    private String clientId;

    @Value("${twbank.secret}")
    private String secret;

    @Value("${twbank.redirectUri}")
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
                .body(forAuthorizationCode(clientId, redirectUri, code))
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
                .body(forUserCredentials(clientId, twUser))
                .retrieve()
                .bodyToMono(TWUserTokensResponse.class)
                .map(twUserTokensResponse -> new TWUserTokens(twUser.getCustomerId(),
                        twUser.getTwUserId(),
                        twUserTokensResponse.getAccessToken(),
                        twUserTokensResponse.getRefreshToken(),
                        now().plusSeconds(twUserTokensResponse.getExpiresIn())));
    }

    @Override
    public Mono<TWUserTokens> refresh(final TWUserTokens twUserTokens) {
        return client.post()
                .uri(OAUTH_TOKEN_PATH)
                .header(AUTHORIZATION, basicAuth())
                .body(forRefreshToken(new RefreshToken(twUserTokens.getRefreshToken())))
                .retrieve()
                .bodyToMono(TWUserTokensResponse.class)
                .map(twUserTokensResponse -> new TWUserTokens(twUserTokens.getCustomerId(),
                        twUserTokens.getTwUserId(),
                        twUserTokensResponse.getAccessToken(),
                        twUserTokensResponse.getRefreshToken(),
                        now().plusSeconds(twUserTokensResponse.getExpiresIn())));
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
                .bodyToMono(TWUserResponse.class)
                .map(twUserResponse -> new TWUser(twUserResponse.getId(),
                        twUserResponse.getEmail(),
                        twUserResponse.getActive()));
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

}
