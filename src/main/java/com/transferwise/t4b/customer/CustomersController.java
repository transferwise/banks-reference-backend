package com.transferwise.t4b.customer;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    private final CustomerRepository customers;

    public CustomersController(final CustomerRepository customers) {
        this.customers = customers;
    }

    @GetMapping
    public Customer show(@RequestParam final Long id) {
        return customers.find(id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Customer create(@Valid @RequestBody final NewCustomer newCustomer) {
        return customers.save(new Customer(newCustomer));
    }
}
