package com.transferwise.banks.demo.transfer.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import com.transferwise.banks.demo.quote.domain.Quote;
import com.transferwise.banks.demo.quote.domain.QuotesService;
import com.transferwise.banks.demo.recipient.domain.Recipient;
import com.transferwise.banks.demo.recipient.domain.RecipientsService;
import com.transferwise.banks.demo.transfer.domain.requirements.Field;
import com.transferwise.banks.demo.transfer.domain.requirements.Group;
import com.transferwise.banks.demo.transfer.domain.requirements.TransferRequirement;
import com.transferwise.banks.demo.transfer.domain.requirements.TransferRequirements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

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
    public Flux<TransferRequirements> requirements(Long customerId, TransferRequest transferRequest) {
        return credentialsManager.refreshTokens(customerId)
                .flatMapMany(twUserTokens -> transfersTWClient.requirements(twUserTokens, transferRequest))
                .map(this::serialiseTransferRequirements);
    }

    private TransferRequirements serialiseTransferRequirements(String s) {
        TransferRequirements transferRequirements = new TransferRequirements();
        try {
            TransferRequirement[] transferRequirementArr = objectMapper.readValue(s, TransferRequirement[].class);
            transferRequirements.setTransferRequirements(List.of(transferRequirementArr));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return transferRequirements;
        }
    }

    //TODO Implement the real thing here & add test to it
    private Flux<TransferRequirements> mockRequirements() {
        Group mockGroup = new Group("reference", "Transfer reference", "text", false, false, null, null, null, 18, null, null, null, null);
        Field mockField = new Field("Transfer reference", List.of(mockGroup), null);
        TransferRequirement requirement = new TransferRequirement("transfer", List.of(mockField), null);
        TransferRequirements requirements = new TransferRequirements(List.of(requirement));
        return Flux.just(requirements);
    }

    @Override
    public Mono<TransferSummary> getTransferSummary(Long customerId, UUID quoteId, Long recipientId) {
        return mockRequirements()
                .map(requirements -> requirements.getFieldByKey(REFERENCE_KEY))
                .map(this::transformIntoValidation)
                .single()
                .flatMap(validation -> quotesService.updateQuote(customerId, quoteId, recipientId)
                        .zipWith(recipientsService.getRecipient(customerId, recipientId))
                        .map(quoteRecipientTuple2 -> buildTransferSummary(quoteRecipientTuple2.getT1(), quoteRecipientTuple2.getT2(), validation)));
    }

    private TransferReferenceValidation transformIntoValidation (final Field field) {
        TransferReferenceValidation validation = null;
        if (field != null) {
            Group group = field.getGroup().stream().filter(g -> REFERENCE_KEY.equals(g.getKey())).findFirst().orElse(null);
            if (group != null) {
                validation = new TransferReferenceValidation(group.getMaxLength(), group.getMinLength(), group.getValidationRegexp());
            }
        }
        return validation;
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
