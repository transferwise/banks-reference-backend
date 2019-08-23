package com.transferwise.t4b.client.params;

public final class RefreshToken extends Parameter {

    public RefreshToken(final String token) {
        super(token);
    }

    @Override
    public String key() {
        return "refresh_token";
    }
}
