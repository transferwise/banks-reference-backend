package com.transferwise.t4b.customer;

import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    private final CustomerRepository customers;

    public CustomersController(final CustomerRepository customers) {
        this.customers = customers;
    }

    @PostMapping
    public Publisher<Customer> create() {
        return Mono.empty();
    }
}
