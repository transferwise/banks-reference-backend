package com.transferwise.t4b.quote;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Quote {

    private Long id;
    private String source;
    private String target;
    private BigDecimal sourceAmount;
    private BigDecimal targetAmount;
    private BigDecimal rate;
    private LocalDateTime deliveryEstimate;
    private BigDecimal fee;

    public Long getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public LocalDateTime getDeliveryEstimate() {
        return deliveryEstimate;
    }

    public BigDecimal getFee() {
        return fee;
    }
}
