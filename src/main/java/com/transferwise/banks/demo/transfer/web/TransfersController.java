package com.transferwise.banks.demo.transfer.web;

import com.transferwise.banks.demo.client.params.TargetAccount;
import com.transferwise.banks.demo.transfer.domain.TransferRequest;
import com.transferwise.banks.demo.transfer.domain.TransferService;
import com.transferwise.banks.demo.transfer.domain.TransferSummary;
import com.transferwise.banks.demo.transfer.domain.TransferWiseTransfer;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/transfers")
public class TransfersController {

    private final TransferService transferService;

    public TransfersController(final TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping(value = "/summary", produces = APPLICATION_JSON_VALUE)
    public Publisher<TransferSummary> createTransferSummary(@RequestParam Long customerId,
                                                         @RequestParam final UUID quoteId,
                                                         @RequestParam final TargetAccount targetAccount) {
        return transferService.getTransferSummary(customerId, quoteId, targetAccount);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Publisher<TransferWiseTransfer> create(@RequestParam final Long customerId, @RequestBody final TransferRequest transferRequest) {
        return transferService.create(customerId, transferRequest);
    }
}
