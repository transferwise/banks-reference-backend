package com.transferwise.banks.demo.transfer.domain;

import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import com.transferwise.banks.demo.quote.domain.QuotesService;
import com.transferwise.banks.demo.recipient.domain.RecipientsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
class TransferServiceImpl implements TransferService {

    private static final Logger log = LoggerFactory.getLogger(TransferServiceImpl.class);

    private final CredentialsManager credentialsManager;
    private final TransfersTWClient transfersTWClient;
    private final QuotesService quotesService;
    private final RecipientsService recipientsService;
    private final CustomerTransferPersistence customerTransferPersistence;

    TransferServiceImpl(CredentialsManager credentialsManager, TransfersTWClient transfersTWClient, QuotesService quotesService, RecipientsService recipientsService, CustomerTransferPersistence customerTransferPersistence) {
        this.credentialsManager = credentialsManager;
        this.transfersTWClient = transfersTWClient;
        this.quotesService = quotesService;
        this.recipientsService = recipientsService;
        this.customerTransferPersistence = customerTransferPersistence;
    }

    @Override
    public Mono<TransferWiseTransfer> create(final Long customerId, final TransferRequest transferRequest) {
        return credentialsManager.refreshTokens(customerId).flatMap(twUserTokens ->
                transfersTWClient.createTransfer(twUserTokens, transferRequest))
                .doOnSuccess(transferWiseTransfer -> quotesService.getQuote(customerId, transferWiseTransfer.getQuoteUuid())
                        .zipWith(recipientsService.getRecipient(customerId, transferRequest.getTargetAccount()))
                        .subscribe(quoteRecipientTuple2 -> {
                            log.info("Saving transfer response {}", transferWiseTransfer);
                            customerTransferPersistence.saveCustomerTransfer(customerId, transferWiseTransfer, quoteRecipientTuple2.getT2(), quoteRecipientTuple2.getT1());
                        }));
    }

    @Override
    public Flux<String> requirements(Long customerId, String requestBody) {
        return credentialsManager.refreshTokens(customerId)
                .flatMapMany(twUserTokens -> transfersTWClient.requirements(twUserTokens, requestBody));
    }
}
