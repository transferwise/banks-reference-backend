package com.transferwise.banks.demo.transfer.domain;

import com.transferwise.banks.demo.client.params.TargetAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TransferService {

    Mono<TransferWiseTransfer> create(Long customerId, TransferRequest transferRequest);

    Flux<String> requirements(Long customerId, String requestBody);

    Mono<TransferSummary> createTransferSummary(Long customerId, UUID quoteId, TargetAccount targetAccount);
}
