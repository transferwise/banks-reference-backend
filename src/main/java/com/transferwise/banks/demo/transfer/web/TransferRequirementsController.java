package com.transferwise.banks.demo.transfer.web;

import com.transferwise.banks.demo.transfer.domain.TransferRequest;
import com.transferwise.banks.demo.transfer.domain.TransferService;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/transfers/requirements")
public class TransferRequirementsController {

    private final TransferService transferService;

    public TransferRequirementsController(final TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Publisher<String> create(@RequestParam final Long customerId, @RequestBody final CreateTransferRequest transferRequest) {
        return transferService.requirements(customerId, mapToTransferRequest(transferRequest));
    }

    private TransferRequest mapToTransferRequest(CreateTransferRequest createTransferRequest) {
        return new TransferRequest(createTransferRequest.getRecipientId(),
                createTransferRequest.getQuoteId(),
                createTransferRequest.getDetails());
    }
}
