package com.transferwise.t4b.credentials;

import com.transferwise.t4b.client.params.Code;
import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.CustomersRepository;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credentials")
public class ExistingUserCredentialsController {

    private final ExistingTransferWiseUser existingUser;
    private final CustomersRepository customers;

    public ExistingUserCredentialsController(final ExistingTransferWiseUser existingUser, final CustomersRepository customers) {
        this.existingUser = existingUser;
        this.customers = customers;
    }

    @PostMapping
    // deeplink, ios android
    public Publisher<Customer> create(@RequestParam final Long customerId, @RequestParam final Code code) {
        final var customer = customers.find(customerId);
        return existingUser
                .attach(code, customer)
                .map(customers::save);
    }
}