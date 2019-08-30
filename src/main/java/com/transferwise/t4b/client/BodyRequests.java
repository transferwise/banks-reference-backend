package com.transferwise.t4b.client;

import com.transferwise.t4b.client.params.*;
import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.PersonalProfileRequest;
import com.transferwise.t4b.customer.TransferwiseUser;
import com.transferwise.t4b.quote.QuoteRequest;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters.MultipartInserter;

import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.springframework.web.reactive.function.BodyInserters.fromMultipartData;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class BodyRequests {

    static BodyInserter<Map<String, String>, ReactiveHttpOutputMessage> forNewUser(final Email email, final V1RegistrationCode v1RegistrationCode) {
        return fromObject(map(email, v1RegistrationCode));
    }

    static MultipartInserter forClientCredentials() {
        return fromMultipartData(multiMap(new GrantTypeClientCredentials()));
    }

    static MultipartInserter forUserCredentials(final TransferWiseBankConfig config, final TransferwiseUser user) {
        return fromMultipartData(
                multiMap(new GrantTypeRegistrationCode(),
                        user.email(),
                        config.clientId(),
                        user.registrationCode()));
    }

    static BodyInserter<String, ReactiveHttpOutputMessage> forPersonalProfile(final Customer customer) {
        return fromObject(new PersonalProfileRequest(customer).toJson());
    }

    static BodyInserter<String, ReactiveHttpOutputMessage> forNewQuote(final QuoteRequest quoteRequest) {
        return fromObject(quoteRequest.toJson());
    }

    static MultipartInserter forRefreshToken(final RefreshToken refreshToken) {
        return fromMultipartData(multiMap(new GrantTypeRefreshToken(), refreshToken));
    }

    static MultipartInserter forCustomerCredentials(final TransferWiseBankConfig config, final Code code) {
        return fromMultipartData(
                multiMap(new GrantTypeAuthorizationCode(),
                        config.clientId(),
                        code,
                        config.redirectUri()));
    }

    private static Map<String, String> map(final Parameter... parameters) {
        return Arrays.stream(parameters).collect(toMap(Parameter::key, Parameter::value));
    }

    static MultiValueMap<String, String> multiMap(final Parameter... parameters) {
        final var multiMap = new LinkedMultiValueMap<String, String>();
        multiMap.setAll(map(parameters));
        return multiMap;
    }
}
