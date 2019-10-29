package com.transferwise.banks.demo.credentials.domain;

import java.time.ZonedDateTime;

public class TWUserTokens {

    private Long customerId;
    private Long twUserId;
    private String accessToken;
    private String refreshToken;
    private ZonedDateTime expiryTime;

    public TWUserTokens(Long customerId, Long twUserId, String accessToken, String refreshToken, ZonedDateTime expiryTime) {
        this.customerId = customerId;
        this.twUserId = twUserId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiryTime = expiryTime;
    }

    public Long getTwUserId() {
        return twUserId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public ZonedDateTime getExpiryTime() {
        return expiryTime;
    }

    public String bearer() {
        return String.format("Bearer %s", accessToken);
    }
}
