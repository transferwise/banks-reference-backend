package com.transferwise.banks.demo.credentials;

import com.transferwise.banks.demo.customer.Customer;
import com.transferwise.banks.demo.customer.CustomersRepository;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-credentials")
public class NewUserCredentialsController {

    private final NewTransferWiseUser newUser;
    private final CustomersRepository customers;

    public NewUserCredentialsController(final NewTransferWiseUser newUser, final CustomersRepository customers) {
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
