package com.transferwise.t4b.transfer;

import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/transfers")
public class TransfersController {

    private final TransferWiseTransfers transfers;

    public TransfersController(final TransferWiseTransfers transfers) {
        this.transfers = transfers;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Publisher<TransferWiseTransfer> create(@RequestParam final Long customerId, @RequestBody final TransferRequest transferRequest) {
        return transfers.create(customerId, transferRequest);
    }
}
