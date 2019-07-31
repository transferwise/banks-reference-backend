package com.transferwise.t4b.client;

import com.transferwise.t4b.customer.Profile;
import com.transferwise.t4b.quote.Quote;
import com.transferwise.t4b.quote.QuoteRequest;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.BodyInserters.MultipartInserter;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Supplier;

import static com.transferwise.t4b.client.TransferWisePaths.*;
import static java.util.Map.entry;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class ApiClient {

    private final WebClient client;
    private final TransferWiseBankConfig config;

    ExchangeFilterFunction printlnFilter = (request, next) -> {
        System.out.println("\n\n" + request.method().toString().toUpperCase() + ":\n\nURL:"
                + request.url().toString() + ":\n\nHeaders:" + request.headers().toString() + "\n\nAttributes:"
                + request.attributes() + "\n\n");

        return next.exchange(request);
    };

    @Autowired
    public ApiClient(final TransferWiseBankConfig config) {
        this.config = config;
        client = WebClient.builder()
                .baseUrl(BASE_URL)
                .filter(printlnFilter).build();
    }

    public Mono<Credentials> accessCredentials(final String code) {
        return authenticationRequest(() -> authenticationBody(code));
    }

    public Mono<Credentials> refresh(final Credentials credentials) {
        return authenticationRequest(() -> refreshTokenBody(credentials.refreshToken));
    }

    public Mono<Credentials> authenticationRequest(final Supplier<MultipartInserter> supplier) {
        return client.post()
                .uri(OAUTH_TOKEN_PATH)
                .header(AUTHORIZATION, basic())
                .body(supplier.get())
                .retrieve()
                .bodyToMono(Credentials.class);
    }

    public Publisher<Profile> profiles(final String token) {
        return getRequest(PROFILES_PATH, token, Profile.class);
    }

    public Publisher<Recipient> recipients(final String token, final Long profileId) {
        return getRequest(ACCOUNTS_PATH, token, Recipient.class, entry("profile", profileId.toString()));
    }

    private <T> Publisher<T> getRequest(final String uri, final String token, final Class<T> result, final Map.Entry<String, String>... params) {
        return client.get()
                .uri(builder -> builder
                        .path(uri)
                        .queryParams(toMultiMap(params))
                        .build())
                .header(AUTHORIZATION, bearer(token))
                .retrieve()
                .bodyToFlux(result);
    }

    private MultiValueMap<String, String> toMultiMap(final Map.Entry<String, String>... entries) {
        final var params = new LinkedMultiValueMap<String, String>();
        params.setAll(Map.ofEntries(entries));
        return params;
    }

    public Mono<Quote> quote(final String token, final QuoteRequest quoteRequest) {
        return client.post()
                .uri("/v1/quotes")
                .header(AUTHORIZATION, bearer(token))
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(quoteRequest.toJson()))
                .retrieve()
                .bodyToMono(Quote.class);
    }

    private MultipartInserter refreshTokenBody(final String refreshToken) {
        final var map = new LinkedMultiValueMap();
        map.add("grant_type", "refresh_token");
        map.add("refresh_token", refreshToken);

        return BodyInserters.fromMultipartData(map);
    }

    private MultipartInserter authenticationBody(final String code) {
        final var map = new LinkedMultiValueMap();
        map.add("grant_type", "authorization_code");
        map.add("client_id", config.clientId());
        map.add("code", code);
        map.add("redirect_uri", config.redirectUri());

        return BodyInserters.fromMultipartData(map);
    }

    private String basic() {
        return String.format("Basic %s", config.encodedCredentials());
    }

    private String bearer(final String token) {
        return String.format("Bearer %s", token);
    }
}
