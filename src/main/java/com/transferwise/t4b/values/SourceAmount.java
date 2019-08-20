package com.transferwise.t4b.values;

import java.math.BigDecimal;
import java.util.Objects;

public final class SourceAmount extends Value<BigDecimal> {

    public SourceAmount(final BigDecimal amount) {
        super(amount);
    }

    public SourceAmount(final Long amount) {
        super(new BigDecimal(amount));
    }

    public boolean isNull() {
        return Objects.isNull(get());
    }
}
