package com.transferwise.banks.demo.quote;

//@Component
public class QuotesService {
/*
    private final CustomersRepository customers;
    private final TransferWiseQuote twQuote;
    private final TransferWiseRecipients twRecipients;

    public QuotesService(CustomersRepository customers, TransferWiseQuote twQuote, TransferWiseRecipients twRecipients) {
        this.customers = customers;
        this.twQuote = twQuote;
        this.twRecipients = twRecipients;
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
                .map(quoteRecipientTuple2 -> new TransferSummary(quoteRecipientTuple2.getT1(), quoteRecipientTuple2.getT2()));
    }*/
}
