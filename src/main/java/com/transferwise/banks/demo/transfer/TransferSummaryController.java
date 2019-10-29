package com.transferwise.banks.demo.transfer;

import com.transferwise.banks.demo.customer.persistence.CustomersRepository;
import com.transferwise.banks.demo.quote.TransferWiseQuote;
import com.transferwise.banks.demo.recipient.domain.Recipient;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/transfers/summary")
public class TransferSummaryController {

    private final TransferWiseQuote quotes;
    private final CustomersRepository customers;

    public TransferSummaryController(final TransferWiseQuote quotes, final CustomersRepository customers) {
        this.quotes = quotes;
        this.customers = customers;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public Publisher<TransferSummary> create(@RequestParam final Long customerId,
                                             @RequestParam final UUID quoteId,
                                             @RequestBody final Recipient recipient) {
        final var customer = customers.find(customerId);
        return quotes
                .update(customer, recipient.targetAccount(), quoteId)
                .map(quote -> new TransferSummary(quote, recipient));
    }
}
