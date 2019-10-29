package com.transferwise.banks.demo.credentials;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credentials")
public class ExistingUserCredentialsController {
/*
    private final ExistingTransferWiseUser existingUser;
    private final CustomersRepository customers;

    public ExistingUserCredentialsController(final ExistingTransferWiseUser existingUser, final CustomersRepository customers) {
        this.existingUser = existingUser;
        this.customers = customers;
    }

    @PostMapping
    // deeplink, ios android
    public Publisher<CustomerEntity> create(@RequestParam final Long customerId, @RequestParam final Code code) {
        final var customer = customers.find(customerId);
        return existingUser
                .attach(code, customer)
                .map(customers::save);
    }*/
}