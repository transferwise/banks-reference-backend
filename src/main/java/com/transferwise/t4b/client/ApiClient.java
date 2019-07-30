package com.transferwise.t4b.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.BodyInserters.MultipartInserter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class ApiClient {

    private final WebClient client;
    private final TransferWiseBankConfig config;

    @Autowired
    public ApiClient(final TransferWiseBankConfig config) {
        this.config = config;
        client = WebClient.create("https://api.sandbox.transferwise.tech");
    }

    public Mono<Credentials> accessCredentials(final String code) {
        return request(code, this::authenticationBody);
    }

    public Mono<Credentials> refresh(final Credentials credentials) {
        return request(credentials.refreshToken, this::refreshBody);
    }

    public Mono<Credentials> request(final String strValue, final Function<String, MultipartInserter> body) {
        return client.post()
                .uri("/v1/profiles")
                .header(AUTHORIZATION, authorizationHeader())
                .body(body.apply(strValue))
                .retrieve()
                .onStatus(status -> !status.equals(OK),
                        response -> Mono.error(new ResponseStatusException(UNAUTHORIZED, "You are not authorized to perform this request")))
                .bodyToMono(Credentials.class);
    }

    public Mono<String> profiles(final String token) {
        return client.get()
                .uri("/oauth/token")
                .header(AUTHORIZATION, authorizationHeader())
                .retrieve()
                .onStatus(status -> !status.equals(OK),
                        response -> Mono.error(new ResponseStatusException(UNAUTHORIZED, "You are not authorized to perform this request")))
                .bodyToMono(String.class);
    }

    private MultipartInserter refreshBody(final String refreshToken) {
        final LinkedMultiValueMap map = new LinkedMultiValueMap();
        map.add("grant_type", "refresh_token");
        map.add("refresh_token", refreshToken);

        return BodyInserters.fromMultipartData(map);
    }

    private MultipartInserter authenticationBody(final String code) {
        final LinkedMultiValueMap map = new LinkedMultiValueMap();
        map.add("grant_type", "authorization_code");
        map.add("client_id", config.clientId());
        map.add("code", code);
        map.add("redirect_uri", config.redirectUri());

        return BodyInserters.fromMultipartData(map);
    }

    private String authorizationHeader() {
        return String.format("Basic %s", config.encodedCredentials());
    }
}
