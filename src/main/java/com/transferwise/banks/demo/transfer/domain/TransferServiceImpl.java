package com.transferwise.banks.demo.transfer.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import com.transferwise.banks.demo.quote.domain.Quote;
import com.transferwise.banks.demo.quote.domain.QuotesService;
import com.transferwise.banks.demo.recipient.domain.Recipient;
import com.transferwise.banks.demo.recipient.domain.RecipientsService;
import com.transferwise.banks.demo.transfer.domain.requirements.Field;
import com.transferwise.banks.demo.transfer.domain.requirements.TransferRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
class TransferServiceImpl implements TransferService {

    private static final Logger log = LoggerFactory.getLogger(TransferServiceImpl.class);
    private static final String REFERENCE_KEY = "reference";

    private final CredentialsManager credentialsManager;
    private final TransfersTWClient transfersTWClient;
    private final QuotesService quotesService;
    private final RecipientsService recipientsService;
    private final CustomerTransferPersistence customerTransferPersistence;
    private final ObjectMapper objectMapper;

    TransferServiceImpl(CredentialsManager credentialsManager, TransfersTWClient transfersTWClient, QuotesService quotesService, RecipientsService recipientsService, CustomerTransferPersistence customerTransferPersistence, ObjectMapper objectMapper) {
        this.credentialsManager = credentialsManager;
        this.transfersTWClient = transfersTWClient;
        this.quotesService = quotesService;
        this.recipientsService = recipientsService;
        this.customerTransferPersistence = customerTransferPersistence;
        this.objectMapper = objectMapper;
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
    public Mono<String> requirements(Long customerId, TransferRequest transferRequest) {
        return credentialsManager.refreshTokens(customerId)
                .flatMap(twUserTokens -> transfersTWClient.requirements(twUserTokens, transferRequest));
    }

    @Override
    public Mono<TransferSummary> getTransferSummary(Long customerId, UUID quoteId, Long recipientId) {
        TransferRequest transferRequest = new TransferRequest(recipientId, quoteId);

        return quotesService.updateQuote(customerId, quoteId, recipientId)
                .zipWith(recipientsService.getRecipient(customerId, recipientId))
                .zipWith(extractTransferReferenceValidation(customerId, transferRequest))
                .map(zipped -> {
                    Quote quote = zipped.getT1().getT1();
                    Recipient recipient = zipped.getT1().getT2();
                    TransferReferenceValidation transferReferenceValidation = zipped.getT2().orElse(null);

                    return buildTransferSummary(quote, recipient, transferReferenceValidation);
                });
    }

    private Mono<Optional<TransferReferenceValidation>> extractTransferReferenceValidation(final Long customerId, final TransferRequest transferRequest) {
        return requirements(customerId, transferRequest)
                .map(json -> {
                    List<TransferRequirement> transferRequirements = new ArrayList<>();
                    try {
                        transferRequirements = objectMapper.readValue(json, new TypeReference<List<TransferRequirement>>() {
                        });


                    } catch (IOException e) {
                        log.error("Error while parsing transfer requirements json", e);
                    }

                    return transferRequirements.stream()
                            .flatMap(transferRequirement -> transferRequirement.getFields().stream())
                            .filter(field -> field.getGroup().stream().anyMatch(f -> REFERENCE_KEY.equals(f.getKey())))
                            .findFirst()
                            .map(this::transformIntoValidation);
                });
    }

    private TransferReferenceValidation transformIntoValidation(final Field field) {
        return field.getGroup().stream()
                .filter(g -> REFERENCE_KEY.equals(g.getKey()))
                .findFirst()
                .map(group -> new TransferReferenceValidation(group.getMinLength(), group.getMaxLength(), group.getValidationRegexp()))
                .orElse(null);
    }

    private TransferSummary buildTransferSummary(final Quote quote, final Recipient recipient, final TransferReferenceValidation referenceValidation) {
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
                quote.getFormattedEstimatedDelivery(),
                referenceValidation);
    }
}
