package com.transferwise.t4b.client;

import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.CustomerRepository;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipients")
public class RecipientsController {

    private final ApiClient client;
    private final CustomerRepository customers;

    public RecipientsController(final ApiClient client, final CustomerRepository customers) {
        this.client = client;
        this.customers = customers;
    }

    @GetMapping
    public Publisher<Recipient> index() {
        return customers
                .findById(1L)
                .map(this::recipients)
                .get();
    }

    private Publisher<Recipient> recipients(final Customer customer) {
        return client.recipients(customer.accessToken(), 6861L);
    }
}
