package com.transferwise.t4b.authorization;

import com.transferwise.t4b.client.Credentials;
import com.transferwise.t4b.client.params.Code;
import com.transferwise.t4b.customer.CredentialsManager;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
public class AuthorizationController {

    private final CredentialsManager credentialsManager;

    public AuthorizationController(final CredentialsManager credentialsManager) {
        this.credentialsManager = credentialsManager;
    }

    @GetMapping
    public Publisher<Credentials> index() {
        return credentialsManager.getCredentials(1L);
    }

    @PostMapping
    // deeplink, ios android
    public Publisher<Credentials> create(@RequestParam final Long customerId, @RequestParam final Code code) {
        return credentialsManager.generate(customerId, code);
    }
}