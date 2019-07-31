package com.transferwise.t4b.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Map;

public class JsonParser {

    public String toJson(final Map<String, ? extends Serializable> params) {
        final ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
