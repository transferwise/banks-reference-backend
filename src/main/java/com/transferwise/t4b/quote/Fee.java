package com.transferwise.t4b.quote;

import java.math.BigDecimal;

public final class Fee {

    private BigDecimal transferwise;
    private BigDecimal payIn;
    private BigDecimal discount;
    private BigDecimal total;

    public BigDecimal getTransferwise() {
        return transferwise;
    }

    public BigDecimal getPayIn() {
        return payIn;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
