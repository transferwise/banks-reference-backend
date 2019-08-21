package com.transferwise.t4b.client;

import com.transferwise.t4b.client.params.Email;
import com.transferwise.t4b.client.params.RegistrationCode;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Map;

public class BodyRequests {

    public static BodyInserter<Map<String, String>, ReactiveHttpOutputMessage> newUser(final Email email) {
        final var registrationCode = new RegistrationCode();

        return BodyInserters.fromObject(Map.of(email.key(), email.value(),
                registrationCode.key(), registrationCode.value()));
    }
}
