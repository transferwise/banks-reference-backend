package com.transferwise.banks.demo.credentials.twclient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.now;

final class TWClientCredentials {

    public final String token;
    public final ZonedDateTime expiresIn;

    @JsonCreator
    public TWClientCredentials(@JsonProperty("access_token") final String token,
                               @JsonProperty("expires_in") final Integer expiresIn) {
        this.token = token;
        this.expiresIn = now().plusSeconds(expiresIn);
    }

    String bearer() {
        return String.format("Bearer %s", token);
    }
}
