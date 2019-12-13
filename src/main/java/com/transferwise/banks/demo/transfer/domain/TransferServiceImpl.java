package com.transferwise.banks.demo.transfer.domain;

import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import com.transferwise.banks.demo.quote.domain.Quote;
import com.transferwise.banks.demo.quote.domain.QuotesService;
import com.transferwise.banks.demo.recipient.domain.Recipient;
import com.transferwise.banks.demo.recipient.domain.RecipientsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

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
        final var customerTransactionId = UUID.randomUUID();
        return credentialsManager.refreshTokens(customerId).flatMap(twUserTokens ->
                transfersTWClient.createTransfer(twUserTokens, transferRequest.withCustomerTransactionId(customerTransactionId)))
                .doOnSuccess(transferWiseTransfer -> quotesService.getQuote(customerId, transferWiseTransfer.getQuoteUuid())
                        .zipWith(recipientsService.getRecipient(customerId, transferRequest.getTargetAccount()))
                        .subscribe(quoteRecipientTuple2 -> {
                            log.info("Saving transfer response {}", transferWiseTransfer);
                            Quote quote = quoteRecipientTuple2.getT1();
                            Recipient recipient = quoteRecipientTuple2.getT2();

                            customerTransferPersistence.saveCustomerTransfer(customerId, transferWiseTransfer, recipient, quote.getFee());
                        }));
    }

    @Override
    public Flux<String> requirements(Long customerId, TransferRequest transferRequest) {
        return credentialsManager.refreshTokens(customerId)
                .flatMapMany(twUserTokens -> transfersTWClient.requirements(twUserTokens, transferRequest));
    }

    @Override
    public Mono<TransferSummary> getTransferSummary(Long customerId, UUID quoteId, Long recipientId) {
        return quotesService.updateQuote(customerId, quoteId, recipientId)
                .zipWith(recipientsService.getRecipient(customerId, recipientId))
                .map(quoteRecipientTuple2 -> buildTransferSummary(quoteRecipientTuple2.getT1(), quoteRecipientTuple2.getT2()));
    }

    private TransferSummary buildTransferSummary(final Quote quote, final Recipient recipient) {
        return new TransferSummary(quote.getId(),
                recipient.getId(),
                quote.getSourceCurrency(),
                quote.getTargetCurrency(),
                quote.getSourceAmount(),
                quote.getTargetAmount(),
                quote.getRate(),
                quote.getFee(),
                recipient.getName().getFullName(),
                recipient.getAccountSummary(),
                quote.getFormattedEstimatedDelivery());
    }
}
