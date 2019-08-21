package com.transferwise.t4b.client;

import com.transferwise.t4b.client.params.*;
import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.Profile;
import com.transferwise.t4b.customer.User;
import com.transferwise.t4b.quote.Quote;
import com.transferwise.t4b.quote.QuoteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.BodyInserters.MultipartInserter;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.function.Supplier;

import static com.transferwise.t4b.client.BodyRequests.newUser;
import static com.transferwise.t4b.client.TransferWisePaths.*;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
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

    public Mono<ClientCredentials> clientCredentials() {
        final var grantTypeClientCredentials =
                BodyInserters.fromMultipartData(multiMap(new GrantTypeClientCredentials()));

        return client.post()
                .uri(OAUTH_TOKEN_PATH)
                .contentType(APPLICATION_FORM_URLENCODED)
                .header(AUTHORIZATION, basic())
                .body(grantTypeClientCredentials)
                .retrieve()
                .bodyToMono(ClientCredentials.class);
    }

    public Mono<User> createUser(final Customer customer) {
        return clientCredentials().flatMap(credentials ->
                client.post()
                        .uri(SIGNUP_PATH)
                        .contentType(APPLICATION_JSON)
                        .header(AUTHORIZATION, bearer(credentials.token))
                        .body(newUser(customer.email()))
                        .retrieve()
                        .bodyToMono(User.class));
    }

    public Mono<Credentials> customerCredentials(final Code code) {
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

    public Flux<Profile> profiles(final String token) {
        return getRequest(PROFILES_PATH, bearer(token)).bodyToFlux(Profile.class);
    }

    public Flux<Recipient> recipients(final String token, final Long profile) {
        return getRequest(ACCOUNTS_PATH, bearer(token), new ProfileId(profile)).bodyToFlux(Recipient.class);
    }

    private WebClient.ResponseSpec getRequest(final String uri, final String authorization, final Param... params) {
        return client.get()
                .uri(builder -> builder
                        .path(uri)
                        .queryParams(multiMap(params))
                        .build())
                .header(AUTHORIZATION, authorization)
                .retrieve();
    }

    private MultiValueMap<String, String> multiMap(final Param... params) {
        final var map = Arrays.stream(params).collect(toMap(Param::key, Param::value));

        final var multiMap = new LinkedMultiValueMap<String, String>();
        multiMap.setAll(map);
        return multiMap;
    }

    public Mono<Quote> quote(final String token, final QuoteRequest quoteRequest) {
        return client.post()
                .uri(QUOTES_PATH)
                .header(AUTHORIZATION, bearer(token))
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(quoteRequest.toJson()))
                .retrieve()
                .bodyToMono(Quote.class);
    }

    public Mono anonymousQuote(final QuoteRequest quoteRequest) {
        return client.post()
                .uri(QUOTES_PATH)
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(quoteRequest.toJson()))
                .retrieve()
                .bodyToMono(Quote.class);
    }

    public Mono<Quote> quote(final String token, final Long quoteId) {
        final var uri = QUOTES_PATH + "/" + quoteId;
        return getRequest(uri, bearer(token)).bodyToMono(Quote.class);
    }

    private MultipartInserter refreshTokenBody(final String refreshToken) {
        final var params = multiMap(new GrantTypeRefreshToken(), new RefreshToken(refreshToken));

        return BodyInserters.fromMultipartData(params);
    }

    private MultipartInserter authenticationBody(final Code code) {
        final var params = multiMap(new GrantTypeAuthorizationCode(),
                new ClientId(config.clientId()),
                code,
                new RedirectUri(config.redirectUri()));

        return BodyInserters.fromMultipartData(params);
    }

    private String basic() {
        return String.format("Basic %s", config.encodedCredentials());
    }

    private String bearer(final String token) {
        return String.format("Bearer %s", token);
    }
}
