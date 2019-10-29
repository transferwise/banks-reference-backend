package com.transferwise.banks.demo.transfer;

import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import com.transferwise.banks.demo.customer.persistence.CustomerEntity;
import com.transferwise.banks.demo.customer.CustomerTransfer;
import com.transferwise.banks.demo.customer.persistence.CustomersRepository;
import com.transferwise.banks.demo.customer.CustomersRepository;
import com.transferwise.banks.demo.quote.Quote;
import com.transferwise.banks.demo.quote.TransferWiseQuote;
import com.transferwise.banks.demo.recipient.domain.Recipient;
import com.transferwise.banks.demo.recipient.TransferWiseRecipients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.transferwise.banks.demo.client.TransferWisePaths.TRANSFERS_PATH;
import static com.transferwise.banks.demo.client.TransferWisePaths.TRANSFER_REQUIREMENTS_PATH;
import static java.util.Collections.emptyList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class TransferService {

    private static final Logger log = LoggerFactory.getLogger(TransferService.class);

    private final WebClient client;
    private final CredentialsManager manager;
    private final CustomersRepository customersRepository;
    private final TransferWiseQuote twQuote;
    private final TransferWiseRecipients twRecipients;

    public TransferService(final WebClient client, final CredentialsManager manager, CustomersRepository customersRepository, TransferWiseQuote twQuote, TransferWiseRecipients twRecipients) {
        this.client = client;
        this.manager = manager;
        this.customersRepository = customersRepository;
        this.twQuote = twQuote;
        this.twRecipients = twRecipients;
    }

    public Flux<String> requirements(final CustomerEntity customerEntity, final String bodyRequest) {
        return manager.credentialsFor(customerEntity).flatMapMany(credentials ->
                client.post()
                        .uri(TRANSFER_REQUIREMENTS_PATH)
                        .header(AUTHORIZATION, credentials.bearer())
                        .contentType(APPLICATION_JSON_UTF8)
                        .body(fromObject(bodyRequest))
                        .retrieve()
                        .bodyToFlux(String.class));
    }

    public Mono<TransferWiseTransfer> create(final Long customerId, final TransferWiseTransfer transferRequest) {
        CustomerEntity customerEntity = customersRepository.find(customerId);

        return manager.credentialsFor(customerEntity).flatMap(credentials ->
                client.post()
                        .uri(TRANSFERS_PATH)
                        .header(AUTHORIZATION, credentials.bearer())
                        .contentType(APPLICATION_JSON_UTF8)
                        .body(fromObject(transferRequest))
                        .retrieve()
                        .bodyToMono(TransferWiseTransfer.class))
                .doOnSuccess(transferWiseTransfer -> {
                    twQuote.get(customer, transferWiseTransfer.getQuoteUuid())
                            .zipWith(twRecipients.getRecipient(customer, transferRequest.getTargetAccount()))
                            .subscribe(quoteRecipientTuple2 -> {
                                log.info("Saving transfer response {}", transferWiseTransfer);
                                CustomerTransfer customerTransfer = mapToCustomerTransfer(transferWiseTransfer, quoteRecipientTuple2.getT2(), quoteRecipientTuple2.getT1());
                                customersRepository.save(customerEntity.addCustomerTransfer(customerTransfer));
                            });
                });
    }

    private CustomerTransfer mapToCustomerTransfer(TransferWiseTransfer transferWiseTransfer, Recipient recipient, Quote quote) {
        return new CustomerTransfer(transferWiseTransfer.getId(),
                transferWiseTransfer.getTargetAccount(),
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
                quote.getFee(),
                emptyList());

    }

}
