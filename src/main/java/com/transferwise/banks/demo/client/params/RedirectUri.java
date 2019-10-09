package com.transferwise.banks.demo.client.params;

public final class RedirectUri extends Parameter {

    public RedirectUri(final String uri) {
        super(uri);
    }

    @Override
    public String key() {
        return "redirect_uri";
    }
}
