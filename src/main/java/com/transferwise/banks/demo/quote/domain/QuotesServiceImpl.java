package com.transferwise.banks.demo.quote.domain;

import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import com.transferwise.banks.demo.credentials.persistence.twprofile.TWProfilePersistence;
import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
class QuotesServiceImpl implements QuotesService {

    private static final String IN_3_DAYS = "in 3 days";

    private final CredentialsManager credentialsManager;
    private final QuotesTWClient quotesTWClient;
    private final TWProfilePersistence twProfilePersistence;

    QuotesServiceImpl(CredentialsManager credentialsManager, QuotesTWClient quotesTWClient, TWProfilePersistence twProfilePersistence) {
        this.credentialsManager = credentialsManager;
        this.quotesTWClient = quotesTWClient;
        this.twProfilePersistence = twProfilePersistence;
    }

    @Override
    public Mono<Quote> createAnonymousQuote(CreateAnonymousQuote createAnonymousQuote) {
        return quotesTWClient.createAnonymousQuote(createAnonymousQuote)
                .map(this::populateQuoteWithCorrectPaymentOptionInfo);
    }

    @Override
    public Mono<Quote> createQuote(Long customerId, CreateQuote createQuote) {
        return twProfilePersistence.findByCustomerId(customerId)
                .map(twProfile -> credentialsManager.refreshTokens(customerId)
                        .flatMap(twUserTokens ->
                                quotesTWClient.createQuote(twUserTokens, createQuote.withProfile(twProfile.getTwProfileId()))
                                        .map(this::populateQuoteWithCorrectPaymentOptionInfo)))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Mono<Quote> updateQuote(Long customerId, UUID quoteId, Long recipientId) {
        return twProfilePersistence.findByCustomerId(customerId)
                .map(twProfile -> credentialsManager.refreshTokens(customerId)
                        .flatMap(twUserTokens -> quotesTWClient.updateQuote(twUserTokens, quoteId, twProfile.getTwProfileId(), recipientId)
                                .map(this::populateQuoteWithCorrectPaymentOptionInfo)))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Mono<Quote> getQuote(Long customerId, UUID quoteId) {
        return credentialsManager.refreshTokens(customerId)
                .flatMap(twUserTokens -> quotesTWClient.getQuote(twUserTokens, quoteId)
                        .map(this::populateQuoteWithCorrectPaymentOptionInfo));
    }


    private Quote populateQuoteWithCorrectPaymentOptionInfo(Quote quote) {
        Optional<PaymentOption> correctPaymentOption = quote.extractCorrectPaymentOption();

        BigDecimal fee = correctPaymentOption
                .map(paymentOption -> paymentOption.getFee().getTotal())
                .orElse(quote.getFee());

        BigDecimal sourceAmount = correctPaymentOption
                .map(PaymentOption::getSourceAmount)
                .orElse(quote.getSourceAmount());

        BigDecimal targetAmount = correctPaymentOption
                .map(PaymentOption::getTargetAmount)
                .orElse(quote.getTargetAmount());


        String formattedEstimatedDelivery = correctPaymentOption
                .map(PaymentOption::getFormattedEstimatedDelivery)
                .orElse(IN_3_DAYS);

        return quote
                .withFee(fee)
                .withSourceAmount(sourceAmount)
                .withTargetAmount(targetAmount)
                .withFormattedEstimatedDelivery(formattedEstimatedDelivery);
    }
}
