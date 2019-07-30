package com.transferwise.t4b.client;

import com.transferwise.t4b.customer.CredentialsManager;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    private final CredentialsManager authenticator;

    public AuthenticationController(final CredentialsManager authenticator) {
        this.authenticator = authenticator;
    }

    @GetMapping
    public Publisher<Credentials> index(@RequestParam final String code) {
        return authenticator.getCredentials(1L, code);
    }
}