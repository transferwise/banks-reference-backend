package com.transferwise.t4b.client.params;

public class RedirectUri implements Param {

    private final String uri;

    public RedirectUri(final String uri) {
        this.uri = uri;
    }

    @Override
    public String key() {
        return "redirect_uri";
    }

    @Override
    public String value() {
        return uri;
    }
}
