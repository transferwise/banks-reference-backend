package com.transferwise.banks.demo.values;

import java.math.BigDecimal;

public final class SourceAmount extends Value<BigDecimal> {

    public SourceAmount(final BigDecimal amount) {
        super(amount);
    }

    public SourceAmount(final Long amount) {
        this(BigDecimal.valueOf(amount));
    }
}
