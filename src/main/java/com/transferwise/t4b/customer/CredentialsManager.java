package com.transferwise.t4b.customer;

import com.transferwise.t4b.client.ApiClient;
import com.transferwise.t4b.client.Credentials;
import com.transferwise.t4b.client.params.Code;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CredentialsManager {

    private final ApiClient client;
    private final CustomerRepository customers;

    public CredentialsManager(final ApiClient client, final CustomerRepository customers) {
        this.client = client;
        this.customers = customers;
    }

    public Mono<Credentials> generate(final Long customerId, final Code code) {
        final var customer = customers.findById(customerId);
        return client
                .customerCredentials(code)
                .doOnSuccess(creds -> save(customer.get(), creds));
    }

    public Mono<Credentials> getCredentials(final Long customerId) {
        final var customer = customers.findById(customerId);
        return customer
                .filter(Customer::hasExpiredCredentials)
                .map(this::refresh)
                .orElseGet(() -> Mono.just(customer.get().credentials));
    }

    private Mono<Credentials> refresh(final Customer customer) {
        return client
                .refresh(customer.credentials)
                .doOnSuccess(creds -> save(customer, creds));
    }

    private Credentials save(final Customer customer, final Credentials credentials) {
        customer.credentials = credentials;
        customers.save(customer);
        return customer.credentials;
    }
}
