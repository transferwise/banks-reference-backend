package com.transferwise.t4b.client;

import com.transferwise.t4b.client.params.Parameter;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.function.Function;

import static com.transferwise.t4b.client.BodyRequests.multiMap;

public class UriWithParams implements Function<UriBuilder, URI> {
    private final String uri;
    private final Parameter[] parameters;

    public UriWithParams(final String uri, final Parameter... parameters) {
        this.uri = uri;
        this.parameters = parameters;
    }

    @Override
    public URI apply(final UriBuilder builder) {
        return builder
                .path(uri)
                .queryParams(multiMap(parameters))
                .build();
    }
}