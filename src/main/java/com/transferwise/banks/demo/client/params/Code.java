package com.transferwise.banks.demo.client.params;

public final class Code extends Parameter {

    public Code(final String code) {
        super(code);
    }

    @Override
    public String key() {
        return "code";
    }
}
