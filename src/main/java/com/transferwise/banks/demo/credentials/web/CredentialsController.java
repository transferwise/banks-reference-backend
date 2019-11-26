package com.transferwise.banks.demo.credentials.web;

import com.transferwise.banks.demo.client.params.Code;
import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import com.transferwise.banks.demo.credentials.domain.twprofile.TWProfile;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-credentials")
public class CredentialsController {

    private final CredentialsManager credentialsManager;

    public CredentialsController(CredentialsManager credentialsManager) {
        this.credentialsManager = credentialsManager;
    }

    @PostMapping("/sign-up")
    public Publisher<TWProfile> signUp(@RequestParam final Long customerId) {
        return credentialsManager.signUp(customerId);
    }

    @PostMapping("/existing")
    public Publisher<TWProfile> existing(@RequestParam final Long customerId, @RequestParam final Code code) {
        return credentialsManager.existing(customerId, code.value());
    }
}
