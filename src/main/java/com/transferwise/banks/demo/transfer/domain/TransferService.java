package com.transferwise.banks.demo.transfer.domain;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TransferService {

    Mono<TransferWiseTransfer> create(Long customerId, TransferRequest transferRequest);

    Mono<String> requirements(Long customerId, TransferRequest transferRequest);

    Mono<TransferSummary> getTransferSummary(Long customerId, UUID quoteId, Long recipientId);
}
