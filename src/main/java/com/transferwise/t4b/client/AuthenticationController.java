package com.transferwise.t4b.client;

import com.transferwise.t4b.customer.CredentialsManager;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    private final CredentialsManager credentialsManager;

    public AuthenticationController(final CredentialsManager credentialsManager) {
        this.credentialsManager = credentialsManager;
    }

    @GetMapping
    public Publisher<Credentials> index(@RequestParam final String code) {
        return credentialsManager.getCredentials(1L, code);
    }

    @PostMapping
    public Publisher<Credentials> create(@RequestParam final String code) {
        return credentialsManager.generate(1L, code);
    }
}