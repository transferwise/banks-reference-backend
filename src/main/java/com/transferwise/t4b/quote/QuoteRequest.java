package com.transferwise.t4b.quote;

import com.transferwise.t4b.client.JsonParser;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class QuoteRequest {

    private Long profile;
    private String source;
    private String target;
    private BigDecimal targetAmount;
    private String type;

    private final JsonParser parser;

    public QuoteRequest() {
        parser = new JsonParser();
    }

    public Long getProfile() {
        return profile;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public String getType() {
        return type;
    }

    public String toJson() {
        return parser.toJson(toMap());
    }

    private Map<String, Serializable> toMap() {
        return Map.of("profile", profile,
                "source", source,
                "target", target,
                "rateType", 1L,
                "targetAmount", targetAmount,
                "type", type);
    }
}
