package com.transferwise.banks.demo.client.params;

public abstract class Parameter {

    private final String value;

    protected Parameter(final String value) {
        this.value = value;
    }

    public abstract String key();

    public String value() {
        return value;
    }
}
