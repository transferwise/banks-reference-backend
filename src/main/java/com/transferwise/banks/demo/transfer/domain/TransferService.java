package com.transferwise.banks.demo.transfer.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransferService {

    Mono<TransferWiseTransfer> create(Long customerId, TransferRequest transferRequest);

    Flux<String> requirements(Long customerId, String requestBody);
}
