package com.transferwise.banks.demo.transfer.domain;

import com.transferwise.banks.demo.client.params.TargetAccount;
import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import com.transferwise.banks.demo.quote.domain.PaymentOption;
import com.transferwise.banks.demo.quote.domain.Quote;
import com.transferwise.banks.demo.quote.domain.QuotesService;
import com.transferwise.banks.demo.recipient.domain.Recipient;
import com.transferwise.banks.demo.recipient.domain.RecipientsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
class TransferServiceImpl implements TransferService {

    private static final Logger log = LoggerFactory.getLogger(TransferServiceImpl.class);

    private static final String BANK_TRANSFER = "BANK_TRANSFER";

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
                            Quote quote = quoteRecipientTuple2.getT1();
                            BigDecimal fee = extractBestPaymentOption(quote)
                                    .map(paymentOption -> paymentOption.getFee().getTotal())
                                    .orElse(quote.getFee());

                            customerTransferPersistence.saveCustomerTransfer(customerId, transferWiseTransfer, quoteRecipientTuple2.getT2(), fee);
                        }));
    }

    @Override
    public Flux<String> requirements(Long customerId, String requestBody) {
        return credentialsManager.refreshTokens(customerId)
                .flatMapMany(twUserTokens -> transfersTWClient.requirements(twUserTokens, requestBody));
    }

    @Override
    public Mono<TransferSummary> createTransferSummary(Long customerId, UUID quoteId, TargetAccount targetAccount) {
        return quotesService.updateQuote(customerId, quoteId, targetAccount)
                .zipWith(recipientsService.getRecipient(customerId, Long.parseLong(targetAccount.value())))
                .map(quoteRecipientTuple2 -> buildTransferSummary(quoteRecipientTuple2.getT1(), quoteRecipientTuple2.getT2()));
    }

    private TransferSummary buildTransferSummary(final Quote quote, final Recipient recipient) {
        Optional<PaymentOption> bestPaymentOption = extractBestPaymentOption(quote);

        BigDecimal fee = bestPaymentOption
                .map(paymentOption -> paymentOption.getFee().getTotal())
                .orElse(quote.getFee());

        BigDecimal sourceAmount = bestPaymentOption
                .map(PaymentOption::getSourceAmount)
                .orElse(quote.getSourceAmount());

        BigDecimal targetAmount = bestPaymentOption
                .map(PaymentOption::getTargetAmount)
                .orElse(quote.getTargetAmount());

        String formattedEstimatedDelivery = bestPaymentOption
                .map(PaymentOption::getFormattedEstimatedDelivery)
                .orElse(null);



        return new TransferSummary(quote.getSourceCurrency(),
                quote.getTargetCurrency(),
                sourceAmount,
                targetAmount,
                quote.getRate(),
                fee,
                recipient.getName().getFullName(),
                recipient.getAccountSummary(),
                formattedEstimatedDelivery);

    }

    private Optional<PaymentOption> extractBestPaymentOption(final Quote quote) {
        return quote.getPaymentOptions()
                .stream()
                .filter(paymentOption ->
                        BANK_TRANSFER.equalsIgnoreCase(paymentOption.getPayIn())
                                && quote.getPayOut().equalsIgnoreCase(paymentOption.getPayOut()))
                .findFirst();
    }
}
