package com.transferwise.t4b.quote;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public final class PaymentOption {

    private Boolean disabled;
    private LocalDateTime estimatedDelivery;
    private String formattedEstimatedDelivery;

    private final List<String> estimatedDeliveryDelays = new ArrayList<>();
    private Fee fee;

    private BigDecimal sourceAmount;
    private BigDecimal targetAmount;
    private String payIn;
    private String payOut;

    private final List<String> allowedProfileTypes = new ArrayList<>();
    private DisabledReason disabledReason;

    public Boolean getDisabled() {
        return disabled;
    }

    public LocalDateTime getEstimatedDelivery() {
        return estimatedDelivery;
    }

    public String getFormattedEstimatedDelivery() {
        return formattedEstimatedDelivery;
    }

    public List<String> getEstimatedDeliveryDelays() {
        return unmodifiableList(estimatedDeliveryDelays);
    }

    public Fee getFee() {
        return fee;
    }

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public String getPayIn() {
        return payIn;
    }

    public String getPayOut() {
        return payOut;
    }

    public List<String> getAllowedProfileTypes() {
        return unmodifiableList(allowedProfileTypes);
    }

    public DisabledReason getDisabledReason() {
        return disabledReason;
    }
}
