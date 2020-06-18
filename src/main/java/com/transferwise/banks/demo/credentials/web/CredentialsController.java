package com.transferwise.banks.demo.credentials.web;

import com.transferwise.banks.demo.client.params.Code;
import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import com.transferwise.banks.demo.customer.address.domain.Address;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user-credentials")
public class CredentialsController {

    private final CredentialsManager credentialsManager;

    public CredentialsController(CredentialsManager credentialsManager) {
        this.credentialsManager = credentialsManager;
    }

    @PostMapping("/sign-up")
    public Publisher<ResponseEntity> signUp(@RequestParam final Long customerId) {
        return credentialsManager.signUp(customerId)
                .map(twProfile -> new ResponseEntity(HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @PostMapping("/existing")
    public Publisher<ResponseEntity> existing(@RequestParam final Long customerId, @RequestParam final Code code) {
        return credentialsManager.existing(customerId, code.value())
                .map(twProfile -> new ResponseEntity(HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}
