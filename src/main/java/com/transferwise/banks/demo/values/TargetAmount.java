package com.transferwise.banks.demo.values;

import java.math.BigDecimal;

public final class TargetAmount extends Value<BigDecimal> {

    public TargetAmount(final BigDecimal amount) {
        super(amount);
    }

    public TargetAmount(final Long amount) {
        this(BigDecimal.valueOf(amount));
    }

}