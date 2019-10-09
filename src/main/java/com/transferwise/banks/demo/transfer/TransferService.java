package com.transferwise.banks.demo.transfer;

import com.transferwise.banks.demo.credentials.CredentialsManager;
import com.transferwise.banks.demo.customer.Customer;
import com.transferwise.banks.demo.customer.CustomerTransfer;
import com.transferwise.banks.demo.customer.CustomersRepository;
import com.transferwise.banks.demo.quote.PaymentOption;
import com.transferwise.banks.demo.recipient.Recipient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.transferwise.banks.demo.client.TransferWisePaths.TRANSFERS_PATH;
import static com.transferwise.banks.demo.client.TransferWisePaths.TRANSFER_REQUIREMENTS_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class TransferService {

    private final WebClient client;
    private final CredentialsManager manager;
    private final CustomersRepository customersRepository;

    public TransferService(final WebClient client, final CredentialsManager manager, CustomersRepository customersRepository) {
        this.client = client;
        this.manager = manager;
        this.customersRepository = customersRepository;
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

    public Mono<TransferWiseTransfer> create(final Long customerId, final TransferRequest transferRequest) {
        Customer customer = customersRepository.find(customerId);

        return manager.credentialsFor(customer).flatMap(credentials ->
                client.post()
                        .uri(TRANSFERS_PATH)
                        .header(AUTHORIZATION, credentials.bearer())
                        .contentType(APPLICATION_JSON_UTF8)
                        .body(fromObject(transferRequest.getTransferWiseTransfer()))
                        .retrieve()
                        .bodyToMono(TransferWiseTransfer.class))
                .doOnSuccess(transferWiseTransfer -> {
                    CustomerTransfer customerTransfer = mapToCustomerTransfer(transferWiseTransfer, transferRequest.getRecipient(), transferRequest.getPaymentOption());
                    customersRepository.save(customer.addCustomerTransfer(customerTransfer));
                });
    }

    private CustomerTransfer mapToCustomerTransfer(TransferWiseTransfer transferWiseTransfer, Recipient recipient, PaymentOption paymentOption) {
        return new CustomerTransfer(transferWiseTransfer.getId(),
                recipient.getId(),
                transferWiseTransfer.getQuoteUuid(),
                transferWiseTransfer.getReference(),
                transferWiseTransfer.getRate(),
                transferWiseTransfer.getCreated(),
                transferWiseTransfer.getSourceCurrency(),
                transferWiseTransfer.getSourceValue(),
                transferWiseTransfer.getTargetCurrency(),
                transferWiseTransfer.getTargetValue(),
                transferWiseTransfer.getCustomerTransactionId(),
                recipient.getName().getFullName(),
                paymentOption.getFee().getTotal());

    }

}
