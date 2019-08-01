package com.transferwise.t4b.client.params;

public class Code implements Param {

    private final String code;

    public Code(final String code) {
        this.code = code;
    }

    @Override
    public String key() {
        return code;
    }

    @Override
    public String value() {
        return null;
    }
}
