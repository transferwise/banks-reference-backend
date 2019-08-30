package com.transferwise.t4b.recipient;

import com.transferwise.t4b.client.ApiClient;
import com.transferwise.t4b.customer.CustomersRepository;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/recipients")
public class RecipientsController {

    private final ApiClient client;
    private final CustomersRepository customers;

    public RecipientsController(final ApiClient client, final CustomersRepository customers) {
        this.client = client;
        this.customers = customers;
    }

    @GetMapping
    public Publisher<Recipient> index(@RequestParam final Long customerId) {
        return customers
                .findById(customerId)
                .map(client::recipients)
                .orElse(Flux.empty());
    }
}
