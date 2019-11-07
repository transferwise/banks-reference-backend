package com.transferwise.banks.demo.credentials.twclient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class TWUserTokensResponse {

    private final String accessToken;
    private final String refreshToken;
    private final Integer expiresIn;

    @JsonCreator
    public TWUserTokensResponse(@JsonProperty("access_token") final String accessToken,
                                @JsonProperty("refresh_token") final String refreshToken,
                                @JsonProperty("expires_in") final Integer expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }
}
