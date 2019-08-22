package com.transferwise.t4b.credentials;

import com.transferwise.t4b.client.params.Code;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credentials")
public class CredentialsController {

    private final CredentialsManager credentialsManager;

    public CredentialsController(final CredentialsManager credentialsManager) {
        this.credentialsManager = credentialsManager;
    }

    // for tests
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