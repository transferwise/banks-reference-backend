package com.transferwise.banks.demo.quote.web;

import com.transferwise.banks.demo.client.JsonParser;
import com.transferwise.banks.demo.values.SourceAmount;
import com.transferwise.banks.demo.values.SourceCurrency;
import com.transferwise.banks.demo.values.TargetAmount;
import com.transferwise.banks.demo.values.TargetCurrency;
import com.transferwise.banks.demo.values.Value;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

@AmountRequired
class AnonymousQuoteRequest {

    @NotNull
    private SourceCurrency sourceCurrency;
    @NotNull
    private TargetCurrency targetCurrency;
    private SourceAmount sourceAmount;
    private TargetAmount targetAmount;

    private final JsonParser parser;

    public AnonymousQuoteRequest() {
        parser = new JsonParser();
    }

    public AnonymousQuoteRequest(final SourceCurrency sourceCurrency, final TargetCurrency targetCurrency,
                                 final SourceAmount sourceAmount, final TargetAmount targetAmount) {
        this();
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.sourceAmount = sourceAmount;
        this.targetAmount = targetAmount;
    }

    public boolean isAmountPresent() {
        return nonNull(sourceAmount) || nonNull(targetAmount);
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
        final var map = new HashMap<String, Serializable>();
        map.putIfAbsent("sourceCurrency", sourceCurrency.get());
        map.putIfAbsent("targetCurrency", targetCurrency.get());
        map.putIfAbsent("sourceAmount", getOrNull(sourceAmount));
        map.putIfAbsent("targetAmount", getOrNull(targetAmount));

        return unmodifiableMap(map);
    }

    private <T> T getOrNull(final Value<T> value) {
        return ofNullable(value).map(Value::get).orElse(null);
    }
}
