package com.transferwise.banks.demo.credentials.persistence.twusertokens;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity(name = "tw_user_tokens")
class TWUserTokensEntity {

    @Id
    private Long customerId;

    private Long twUserId;
    private String accessToken;
    private String refreshToken;
    private ZonedDateTime expiryTime;

    public TWUserTokensEntity() {
    }

    public TWUserTokensEntity(Long customerId, Long twUserId, String accessToken, String refreshToken, ZonedDateTime expiryTime) {
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
}
