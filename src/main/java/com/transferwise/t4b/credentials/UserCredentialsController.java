package com.transferwise.t4b.credentials;

import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.CustomersRepository;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-credentials")
public class UserCredentialsController {

    private final NewTransferWiseUser newUser;
    private final CustomersRepository customers;

    public UserCredentialsController(final NewTransferWiseUser newUser, final CustomersRepository customers) {
        this.newUser = newUser;
        this.customers = customers;
    }

    @PostMapping
    public Publisher<TransferwiseCredentials> create(@RequestParam final Long id) {
        final var customer = customers.find(id);
        return newUser.create(customer)
                .map(customers::save)
                .map(Customer::getCredentials);
    }
}
