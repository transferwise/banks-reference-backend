package com.transferwise.t4b.client;

import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.CustomerRepository;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/profiles")
public class ProfilesController {

    private final ApiClient client;
    private final CustomerRepository customers;

    public ProfilesController(final ApiClient client, final CustomerRepository customers) {
        this.client = client;
        this.customers = customers;
    }

    @GetMapping
    public Publisher<String> index() {
        final Optional<Credentials> credentials = customers.findById(1L).map(c -> c.credentials);
        credentials.map(data -> Mono.justOrEmpty(data)).flatMap(this::profiles);
        return credentials.map(this::profiles);
    }

    private Mono<String> profiles(final Customer customer) {
        return client.profiles(customer.credentials.accessToken);
    }
}
