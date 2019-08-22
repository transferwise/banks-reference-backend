package com.transferwise.t4b.client;

import com.transferwise.t4b.client.params.*;
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

    public static BodyInserter<Map<String, String>, ReactiveHttpOutputMessage> forNewUser(final Email email) {
        return fromObject(map(email, new RegistrationCode()));
    }

    public static MultipartInserter forClientCredentials() {
        return fromMultipartData(multiMap(new GrantTypeClientCredentials()));
    }

    public static MultipartInserter forRefreshToken(final RefreshToken refreshToken) {
        return fromMultipartData(multiMap(new GrantTypeRefreshToken(), refreshToken));
    }

    private static Map<String, String> map(final Param... params) {
        return Arrays.stream(params).collect(toMap(Param::key, Param::value));
    }

    private static MultiValueMap<String, String> multiMap(final Param... params) {
        final var multiMap = new LinkedMultiValueMap<String, String>();
        multiMap.setAll(map(params));
        return multiMap;
    }
}
