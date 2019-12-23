package com.transferwise.banks.demo.transfer.domain;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import reactor.core.publisher.Mono;

public interface TransfersTWClient {

    Mono<TransferWiseTransfer> createTransfer(TWUserTokens twUserTokens, TransferRequest transferRequest);

    Mono<String> requirements(TWUserTokens twUserTokens, TransferRequest transferRequest);
}
