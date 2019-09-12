package com.transferwise.t4b.transfer;

import com.transferwise.t4b.credentials.CredentialsManager;
import com.transferwise.t4b.customer.Customer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static com.transferwise.t4b.client.TransferWisePaths.TRANSFER_REQUIREMENTS_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class TransferWiseTransfers {

    private final WebClient client;
    private final CredentialsManager manager;

    public TransferWiseTransfers(final WebClient client, final CredentialsManager manager) {
        this.client = client;
        this.manager = manager;
    }

    public Flux<String> requirements(final Customer customer, final String bodyRequest) {
        return manager.credentialsFor(customer).flatMapMany(credentials ->
                client.post()
                        .uri(TRANSFER_REQUIREMENTS_PATH)
                        .header(AUTHORIZATION, credentials.bearer())
                        .contentType(APPLICATION_JSON_UTF8)
                        .body(fromObject(bodyRequest))
                        .retrieve()
                        .bodyToFlux(String.class));
    }
}
