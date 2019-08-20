package com.transferwise.t4b.values;

public abstract class Value<T> {

    private final T value;

    public Value(final T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}
