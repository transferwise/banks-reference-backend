package com.transferwise.t4b.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

    public String toJson(final Object params) {
        final ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writer().writeValueAsString(params);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
