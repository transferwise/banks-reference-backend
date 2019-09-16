package com.transferwise.t4b.values;

import java.math.BigDecimal;

public final class SourceAmount extends Value<BigDecimal> {

    public SourceAmount(final BigDecimal amount) {
        super(amount);
    }

    public SourceAmount(final Long amount) {
        this(new BigDecimal(amount));
    }
}
