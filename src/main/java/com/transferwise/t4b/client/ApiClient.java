package com.transferwise.t4b.client;

import com.transferwise.t4b.client.params.Code;
import com.transferwise.t4b.client.params.Parameter;
import com.transferwise.t4b.client.params.RegistrationCode;
import com.transferwise.t4b.credentials.Credentials;
import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.Profile;
import com.transferwise.t4b.customer.User;
import com.transferwise.t4b.quote.Quote;
import com.transferwise.t4b.quote.QuoteRequest;
import com.transferwise.t4b.recipient.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static com.transferwise.t4b.client.BodyRequests.*;
import static com.transferwise.t4b.client.TransferWisePaths.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromDataBuffers;

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

    public Mono<Customer> userCredentials(final Customer customer) {
        final var registrationCode = new RegistrationCode();

        return clientCredentials().flatMap(credentials ->
                createUser(customer, credentials, registrationCode)
                        .map(user -> user.withRegistrationCode(registrationCode))
                        .map(customer::withUser)
                        .flatMap(c -> createUserCredentials(c.getUser(), credentials))
                        .map(customer::withCredentials)
                        .flatMap(this::createPersonalProfile)
                        .map(customer::withPersonalProfile));
    }

    private Mono<ClientCredentials> clientCredentials() {
        return client.post()
                .uri(OAUTH_TOKEN_PATH)
                .contentType(APPLICATION_FORM_URLENCODED)
                .header(AUTHORIZATION, basic())
                .body(forClientCredentials())
                .retrieve()
                .bodyToMono(ClientCredentials.class);
    }

    private Mono<User> createUser(final Customer customer,
                                  final ClientCredentials credentials,
                                  final RegistrationCode registrationCode) {
        return client.post()
                .uri(SIGNUP_PATH)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, bearer(credentials.token))
                .body(forNewUser(customer.email(), registrationCode.v1()))
                .retrieve()
                .bodyToMono(User.class);
    }

    private Mono<Credentials> createUserCredentials(final User user, final ClientCredentials credentials) {
        return client.post()
                .uri(OAUTH_TOKEN_PATH)
                .contentType(APPLICATION_FORM_URLENCODED)
                .header(AUTHORIZATION, basic())
                .body(forUserCredentials(config, user))
                .retrieve()
                .bodyToMono(Credentials.class);
    }

    private Mono<Profile> createPersonalProfile(final Customer customer) {
        return client.post()
                .uri(PROFILES_PATH_V1)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, bearer(customer))
                .body(forPersonalProfile(customer))
                .retrieve()
                .bodyToMono(Profile.class);
    }

    public Mono<Customer> attachCredentialsAndProfiles(final Code code, final Customer customer) {
        return createCustomerCredentials(code)
                .map(customer::withCredentials)
                .map(this::profiles)
                .flatMap(Flux::collectList)
                .map(customer::withProfiles);
    }

    private Mono<Credentials> createCustomerCredentials(final Code code) {
        return client.post()
                .uri(OAUTH_TOKEN_PATH)
                .header(AUTHORIZATION, basic())
                .body(forCustomerCredentials(config, code))
                .retrieve()
                .bodyToMono(Credentials.class);
    }

    private Flux<Profile> profiles(final Customer customer) {
        return client
                .get()
                .uri(PROFILES_PATH_V2)
                .header(AUTHORIZATION, bearer(customer))
                .retrieve()
                .bodyToFlux(Profile.class);
    }

    public Flux<Recipient> recipients(final Customer customer) {
        return getRequest(ACCOUNTS_PATH, bearer(customer), customer.personalProfileId())
                .bodyToFlux(Recipient.class);
    }

    private WebClient.ResponseSpec getRequest(final String uri, final String authorization, final Parameter... parameters) {
        return client.get()
                .uri(builder -> builder
                        .path(uri)
                        .queryParams(multiMap(parameters))
                        .build())
                .header(AUTHORIZATION, authorization)
                .retrieve();
    }

    public Mono<Credentials> refresh(final Credentials credentials) {
        if (credentials.areExpired()) {
            return client.post()
                    .uri(OAUTH_TOKEN_PATH)
                    .header(AUTHORIZATION, basic())
                    .body(forRefreshToken(credentials.refreshToken()))
                    .retrieve()
                    .bodyToMono(Credentials.class);
        }

        return Mono.just(credentials);
    }

    private <T> Mono<T> withCustomerCredentials(final Customer customer, final Function<Credentials, Mono<T>> f) {
        return refresh(customer.getCredentials()).flatMap(f);
    }

    public Mono<Quote> quote(final Customer customer, final QuoteRequest quoteRequest) {
        return withCustomerCredentials(customer, credentials ->
                client.post()
                        .uri(QUOTES_PATH_V2)
                        .header(AUTHORIZATION, bearer(credentials))
                        .contentType(APPLICATION_JSON)
                        .body(forNewQuote(quoteRequest))
                        .retrieve()
                        .bodyToMono(Quote.class));
    }

    public Mono anonymousQuote(final QuoteRequest quoteRequest) {
        return client.post()
                .uri(QUOTES_PATH_V2)
                .contentType(APPLICATION_JSON)
                .body(forNewQuote(quoteRequest))
                .retrieve()
                .bodyToMono(Quote.class);
    }

    public Mono<String> proxy(final Customer customer) {
        return client.get()
                .uri(recipientRequirementsPath(customer.latestQuoteId()))
                .header(AUTHORIZATION, bearer(customer))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> proxy(final Customer customer, final ServerHttpRequest request) {
        return client.post()
                .uri(recipientRequirementsPath(customer.latestQuoteId()))
                .header(AUTHORIZATION, bearer(customer))
                .headers(headers -> headers.addAll(request.getHeaders()))
                .body(fromDataBuffers(request.getBody()))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<Quote> quote(final String token, final Long quoteId) {
        final var uri = QUOTES_PATH_V2 + "/" + quoteId;
        return getRequest(uri, bearer(token)).bodyToMono(Quote.class);
    }

    private String basic() {
        return String.format("Basic %s", config.encodedCredentials());
    }

    private String bearer(final Credentials credentials) {
        return String.format("Bearer %s", credentials.accessToken);
    }

    private String bearer(final Customer customer) {
        return String.format("Bearer %s", customer.accessToken());
    }

    private String bearer(final String token) {
        return String.format("Bearer %s", token);
    }
}
