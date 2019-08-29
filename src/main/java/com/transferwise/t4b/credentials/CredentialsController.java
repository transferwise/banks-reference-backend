package com.transferwise.t4b.credentials;

import com.transferwise.t4b.client.params.Code;
import com.transferwise.t4b.customer.Customer;
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
    public Publisher<Customer> create(@RequestParam final Long customerId, @RequestParam final Code code) {
        return credentialsManager.generate(customerId, code);
    }
}