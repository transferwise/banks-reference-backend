package com.transferwise.t4b.quote;

import com.transferwise.t4b.client.JsonParser;
import com.transferwise.t4b.validations.AmountRequired;
import com.transferwise.t4b.values.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

import static java.util.Objects.nonNull;

@AmountRequired
public class QuoteRequest {

    @NotNull
    private Profile profile;
    @NotNull
    private SourceCurrency sourceCurrency;
    @NotNull
    private TargetCurrency targetCurrency;

    private SourceAmount sourceAmount;
    private TargetAmount targetAmount;

    private final JsonParser parser;

    public QuoteRequest() {
        parser = new JsonParser();
    }

    public QuoteRequest(final Profile profile, final SourceCurrency sourceCurrency, final TargetCurrency targetCurrency,
                        final SourceAmount sourceAmount, final TargetAmount targetAmount) {
        this();
        this.profile = profile;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.sourceAmount = sourceAmount;
        this.targetAmount = targetAmount;
    }

    public boolean isAmountPresent() {
        return nonNull(sourceAmount) || nonNull(targetAmount);
    }

    public Profile getProfile() {
        return profile;
    }

    public SourceCurrency getSourceCurrency() {
        return sourceCurrency;
    }

    public TargetCurrency getTargetCurrency() {
        return targetCurrency;
    }

    public SourceAmount getSourceAmount() {
        return sourceAmount;
    }

    public TargetAmount getTargetAmount() {
        return targetAmount;
    }

    public String toJson() {
        return parser.toJson(toMap());
    }

    private Map<String, Serializable> toMap() {
        return Map.of("profile", profile.get(),
                "sourceCurrency", sourceCurrency.get(),
                "targetCurrency", targetCurrency.get(),
                "sourceAmount", sourceAmount.get(),
                "targetAmount", targetAmount.get());
    }
}
