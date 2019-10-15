package com.transferwise.banks.demo.transfer.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/transfers/status")
public class TransferStatusController {

    private static final Logger log = LoggerFactory.getLogger(TransferStatusController.class);

    private final TransferStatusService transferStatusService;

    @Autowired
    public TransferStatusController(TransferStatusService transferStatusService) {
        this.transferStatusService = transferStatusService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void transferStatusChange(@RequestBody TransferStatusChangeEvent transferStatusChangeEvent) {
        log.info("transferStatusChange {}", transferStatusChangeEvent);

        final var customerTransferStatus = transferStatusChangeEvent.toCustomerTransferStatus();
        transferStatusService.transferStatusChange(customerTransferStatus);

    }
}
