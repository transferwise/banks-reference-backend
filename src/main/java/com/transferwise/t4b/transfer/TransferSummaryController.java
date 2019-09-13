package com.transferwise.t4b.transfer;

import com.transferwise.t4b.client.params.TargetAccount;
import com.transferwise.t4b.customer.CustomersRepository;
import com.transferwise.t4b.quote.Quote;
import com.transferwise.t4b.quote.TransferWiseQuote;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/transfers/summary")
public class TransferSummaryController {

    private final TransferWiseTransfers transfers;
    private final TransferWiseQuote quotes;
    private final CustomersRepository customers;

    public TransferSummaryController(final TransferWiseTransfers transfers, final TransferWiseQuote quotes, final CustomersRepository customers) {
        this.transfers = transfers;
        this.quotes = quotes;
        this.customers = customers;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Publisher<Quote> create(@RequestParam final Long customerId,
                                   @RequestParam final TargetAccount targetAccount) {
        final var customer = customers.find(customerId);
        return quotes.update(customer, targetAccount);
    }
}
