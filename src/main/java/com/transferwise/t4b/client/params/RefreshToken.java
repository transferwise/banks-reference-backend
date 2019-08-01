package com.transferwise.t4b.client.params;

public class RefreshToken implements Param {

    private final String token;

    public RefreshToken(final String token) {
        this.token = token;
    }

    @Override
    public String key() {
        return "refresh_token";
    }

    @Override
    public String value() {
        return token;
    }
}
