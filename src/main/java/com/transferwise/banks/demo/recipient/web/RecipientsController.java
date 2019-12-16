package com.transferwise.banks.demo.recipient.web;

import com.transferwise.banks.demo.recipient.domain.Recipient;
import com.transferwise.banks.demo.recipient.domain.RecipientsService;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/recipients")
public class RecipientsController {

    private final RecipientsService recipientsService;

    public RecipientsController(RecipientsService recipientsService) {
        this.recipientsService = recipientsService;
    }


    @GetMapping
    public Publisher<Recipient> index(@RequestParam final Long customerId, @RequestParam(required = false) final String currencyCode) {
        return recipientsService.getAllRecipients(customerId, currencyCode);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Mono<String> create(@RequestParam final Long customerId, final HttpEntity<String> rawRequest) {
        return recipientsService.create(customerId, rawRequest.getBody());
    }
}
