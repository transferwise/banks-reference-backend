package com.transferwise.banks.demo.client;

import com.transferwise.banks.demo.client.params.ClientId;
import com.transferwise.banks.demo.client.params.Code;
import com.transferwise.banks.demo.client.params.Email;
import com.transferwise.banks.demo.client.params.GrantTypeAuthorizationCode;
import com.transferwise.banks.demo.client.params.GrantTypeRefreshToken;
import com.transferwise.banks.demo.client.params.GrantTypeRegistrationCode;
import com.transferwise.banks.demo.client.params.Parameter;
import com.transferwise.banks.demo.client.params.ProfileId;
import com.transferwise.banks.demo.client.params.RedirectUri;
import com.transferwise.banks.demo.client.params.RefreshToken;
import com.transferwise.banks.demo.client.params.RegistrationCode;
import com.transferwise.banks.demo.client.params.TargetAccount;
import com.transferwise.banks.demo.credentials.domain.TWUser;
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

    public static MultipartInserter forRefreshToken(final RefreshToken refreshToken) {
        return fromMultipartData(multiMap(new GrantTypeRefreshToken(), refreshToken));
    }

    public static MultipartInserter forAuthorizationCode(final String clientId, final String redirectUri, final String code) {
        return fromMultipartData(
                multiMap(new GrantTypeAuthorizationCode(),
                        new ClientId(clientId),
                        new Code(code),
                        new RedirectUri(redirectUri)));
    }

    public static MultipartInserter forUserCredentials(final String clientId, final TWUser twUser) {
        return fromMultipartData(
                multiMap(new GrantTypeRegistrationCode(),
                        new Email(twUser.getEmail()),
                        new ClientId(clientId),
                        new RegistrationCode(twUser.getRegistrationCode())));
    }

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
