package com.transferwise.t4b.client;

import com.transferwise.t4b.client.params.Code;
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
    public Publisher<Credentials> index() {
        return credentialsManager.getCredentials(1L);
    }

    @PostMapping
    public Publisher<Credentials> create(@RequestParam final Code code) {
        return credentialsManager.generate(1L, code);
    }
}