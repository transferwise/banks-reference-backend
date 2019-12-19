package com.transferwise.banks.demo.transfer.twclient;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.transfer.domain.TransferRequest;
import com.transferwise.banks.demo.transfer.domain.TransferWiseTransfer;
import com.transferwise.banks.demo.transfer.domain.TransfersTWClient;
import com.transferwise.banks.demo.transfer.domain.requirements.TransferRequirements;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.transferwise.banks.demo.client.TransferWisePaths.TRANSFERS_PATH;
import static com.transferwise.banks.demo.client.TransferWisePaths.TRANSFER_REQUIREMENTS_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
class TransfersTWClientImpl implements TransfersTWClient {

    private final WebClient client;

    TransfersTWClientImpl(WebClient client) {
        this.client = client;
    }

    @Override
    public Mono<TransferWiseTransfer> createTransfer(TWUserTokens twUserTokens, TransferRequest transferRequest) {
        return client.post()
                .uri(TRANSFERS_PATH)
                .header(AUTHORIZATION, twUserTokens.bearer())
                .contentType(APPLICATION_JSON_UTF8)
                .body(fromObject(transferRequest))
                .retrieve()
                .bodyToMono(TransferWiseTransfer.class);
    }

    @Override
    public Flux<TransferRequirements> requirements(TWUserTokens twUserTokens, TransferRequest transferRequest) {
        return client.post()
                .uri(TRANSFER_REQUIREMENTS_PATH)
                .header(AUTHORIZATION, twUserTokens.bearer())
                .contentType(APPLICATION_JSON_UTF8)
                .body(fromObject(transferRequest))
                .retrieve()
                .bodyToFlux(TransferRequirements.class);
    }
}
