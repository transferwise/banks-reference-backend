package com.transferwise.banks.demo.transfer.domain;

import com.transferwise.banks.demo.transfer.domain.requirements.TransferRequirements;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TransferService {

    Mono<TransferWiseTransfer> create(Long customerId, TransferRequest transferRequest);

    Flux<TransferRequirements> requirements(Long customerId, TransferRequest transferRequest);

    Mono<TransferSummary> getTransferSummary(Long customerId, UUID quoteId, Long recipientId);
}
