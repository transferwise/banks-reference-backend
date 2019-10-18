package com.transferwise.banks.demo.quote;

import com.transferwise.banks.demo.client.params.TargetAccount;
import com.transferwise.banks.demo.customer.CustomersRepository;
import com.transferwise.banks.demo.recipient.Recipient;
import com.transferwise.banks.demo.recipient.TransferWiseRecipients;
import com.transferwise.banks.demo.transfer.TransferSummary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class QuotesService {

    private final CustomersRepository customers;
    private final TransferWiseQuote twQuote;
    private final TransferWiseRecipients twRecipients;
    private final PaymentOptionService paymentOptionService;

    public QuotesService(CustomersRepository customers, TransferWiseQuote twQuote, TransferWiseRecipients twRecipients, PaymentOptionService paymentOptionService) {
        this.customers = customers;
        this.twQuote = twQuote;
        this.twRecipients = twRecipients;
        this.paymentOptionService = paymentOptionService;
    }


    public Mono<Quote> createQuote(Long customerId, QuoteRequest quoteRequest) {
        final var customer = customers.find(customerId);
        return twQuote
                .create(customer, quoteRequest);
    }

    public Mono<TransferSummary> updateQuote(Long customerId, TargetAccount targetAccount, UUID quoteId) {
        final var customer = customers.find(customerId);

        Mono<Recipient> recipientMono = twRecipients.getRecipient(customer, Long.valueOf(targetAccount.value()));

        return twQuote
                .update(customer, targetAccount, quoteId)
                .zipWith(recipientMono)
                .map(quoteRecipientTuple2 -> {
                    PaymentOption paymentOption = paymentOptionService.getBestPaymentOption(quoteRecipientTuple2.getT1(), quoteRecipientTuple2.getT2().getType());

                    return new TransferSummary(paymentOption, quoteRecipientTuple2.getT1(), quoteRecipientTuple2.getT2());
                });
    }
}
