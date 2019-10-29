package com.transferwise.banks.demo.client;

import com.transferwise.banks.demo.client.params.Email;
import com.transferwise.banks.demo.client.params.GrantTypeClientCredentials;
import com.transferwise.banks.demo.client.params.GrantTypeRefreshToken;
import com.transferwise.banks.demo.client.params.Parameter;
import com.transferwise.banks.demo.client.params.ProfileId;
import com.transferwise.banks.demo.client.params.RefreshToken;
import com.transferwise.banks.demo.client.params.TargetAccount;
import com.transferwise.banks.demo.client.params.V1RegistrationCode;
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

    public static BodyInserter<Map<String, String>, ReactiveHttpOutputMessage> forNewUser(final Email email, final V1RegistrationCode v1RegistrationCode) {
        return fromObject(map(email, v1RegistrationCode));
    }

    public static MultipartInserter forClientCredentials() {
        return fromMultipartData(multiMap(new GrantTypeClientCredentials()));
    }

    /*public static MultipartInserter forUserCredentials(final TransferWiseBankConfig config, final TransferwiseUser user) {
        return fromMultipartData(
                multiMap(new GrantTypeRegistrationCode(),
                        user.email(),
                        config.clientId(),
                        user.registrationCode()));
    }*/
/*
    public static BodyInserter<String, ReactiveHttpOutputMessage> forPersonalProfile(final CustomerEntity customerEntity) {
        return fromObject(new PersonalProfileRequest(customerEntity).toJson());
    }

    public static BodyInserter<String, ReactiveHttpOutputMessage> forNewQuote(final QuoteRequest quoteRequest) {
        return fromObject(quoteRequest.toJson());
    }*/

    public static MultipartInserter forRefreshToken(final RefreshToken refreshToken) {
        return fromMultipartData(multiMap(new GrantTypeRefreshToken(), refreshToken));
    }

    /*public static MultipartInserter forCustomerCredentials(final TransferWiseBankConfig config, final Code code) {
        return fromMultipartData(
                multiMap(new GrantTypeAuthorizationCode(),
                        config.clientId(),
                        code,
                        config.redirectUri()));
    }
*/
    public static BodyInserter<Map<String, String>, ReactiveHttpOutputMessage> forQuoteUpdate(final ProfileId profileId, final TargetAccount targetAccount) {
        return fromObject(map(profileId, targetAccount));
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
