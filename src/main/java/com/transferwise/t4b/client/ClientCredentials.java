package com.transferwise.t4b.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.now;

public final class ClientCredentials {

    public final String token;
    public final ZonedDateTime expiresIn;

    @JsonCreator
    public ClientCredentials(@JsonProperty("access_token") final String token,
                             @JsonProperty("expires_in") final Integer expiresIn) {
        this.token = token;
        this.expiresIn = now().plusSeconds(expiresIn);
    }
}
