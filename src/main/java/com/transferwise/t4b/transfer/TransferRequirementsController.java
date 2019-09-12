package com.transferwise.t4b.transfer;

import com.transferwise.t4b.customer.CustomersRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/transfers/requirements")
public class TransferRequirementsController {

    private final TransferWiseTransfers transfers;
    private final CustomersRepository customers;

    public TransferRequirementsController(final TransferWiseTransfers transfers, final CustomersRepository customers) {
        this.transfers = transfers;
        this.customers = customers;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Publisher<String> create(@RequestParam final Long customerId, final HttpEntity<String> rawRequest) {
        final var customer = customers.find(customerId);
        return transfers.requirements(customer, rawRequest.getBody());
    }
}
