package com.transferwise.banks.demo.customer.web;

import com.transferwise.banks.demo.customer.domain.Customer;
import com.transferwise.banks.demo.customer.domain.CustomersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    private final CustomersService customersService;

    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @GetMapping
    public Customer show(@RequestParam final Long id) {
        return customersService.find(id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Customer create(@Valid @RequestBody final NewCustomerRequest newCustomerRequest) {
        return customersService.save(mapToCustomer(newCustomerRequest));
    }

    private Customer mapToCustomer(final NewCustomerRequest newCustomerRequest) {
        return new Customer(newCustomerRequest.getFirstName(),
                newCustomerRequest.getLastName(),
                newCustomerRequest.getDateOfBirth(),
                newCustomerRequest.getPhoneNumber(),
                newCustomerRequest.getEmail());
    }
}
