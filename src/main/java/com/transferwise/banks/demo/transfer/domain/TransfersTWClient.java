package com.transferwise.banks.demo.transfer.domain;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransfersTWClient {

    Mono<TransferWiseTransfer> createTransfer(TWUserTokens twUserTokens, TransferRequest transferRequest);

    Flux<String> requirements(TWUserTokens twUserTokens, TransferRequest transferRequest);
}
