package com.transferwise.banks.demo.recipient.web;

import com.transferwise.banks.demo.recipient.domain.RecipientsService;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/recipient/requirements")
public class RecipientRequirementsController {

    private final RecipientsService recipientsService;

    public RecipientRequirementsController(RecipientsService recipientsService) {
        this.recipientsService = recipientsService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Publisher<List<Map>> get(@RequestParam final Long customerId, @RequestParam final UUID quoteId) {
        return recipientsService.getRequirements(customerId, quoteId);

        /*final var customer = customers.find(customerId);
        return twRecipients.requirements(customer, quoteId);*/
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Publisher<String> create(@RequestParam final Long customerId, final HttpEntity<String> rawRequest, @RequestParam final UUID quoteId) {
        return recipientsService.createRequirements(customerId, quoteId, rawRequest.getBody());
        /*final var customer = customers.find(customerId);
        return twRecipients.requirements(customer, rawRequest.getBody(), quoteId);*/
    }
}
