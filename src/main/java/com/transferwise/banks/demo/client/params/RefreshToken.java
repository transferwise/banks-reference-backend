package com.transferwise.banks.demo.client.params;

public final class RefreshToken extends Parameter {

    public RefreshToken(final String token) {
        super(token);
    }

    @Override
    public String key() {
        return "refresh_token";
    }
}
