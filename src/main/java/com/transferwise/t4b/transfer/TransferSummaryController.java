package com.transferwise.t4b.transfer;

import com.transferwise.t4b.customer.CustomersRepository;
import com.transferwise.t4b.quote.TransferWiseQuote;
import com.transferwise.t4b.recipient.Recipient;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;

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
                                             @RequestBody final Recipient recipient) {
        final var customer = customers.find(customerId);
        return quotes
                .update(customer, recipient.targetAccount())
                .map(quote -> new TransferSummary(quote, recipient));
    }
}
