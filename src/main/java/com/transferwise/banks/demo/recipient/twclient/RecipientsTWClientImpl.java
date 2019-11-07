package com.transferwise.banks.demo.recipient.twclient;

import com.transferwise.banks.demo.client.UriWithParams;
import com.transferwise.banks.demo.client.params.ProfileId;
import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.recipient.domain.Recipient;
import com.transferwise.banks.demo.recipient.domain.RecipientsTWClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.transferwise.banks.demo.client.TransferWisePaths.RECIPIENTS_PATH_V1;
import static com.transferwise.banks.demo.client.TransferWisePaths.RECIPIENTS_PATH_V2;
import static com.transferwise.banks.demo.client.TransferWisePaths.recipientByIdPath;
import static com.transferwise.banks.demo.client.TransferWisePaths.recipientRequirementsPath;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static reactor.core.publisher.Flux.fromIterable;

@Component
class RecipientsTWClientImpl implements RecipientsTWClient {

    private final WebClient client;

    RecipientsTWClientImpl(WebClient client) {
        this.client = client;
    }

    @Override
    public Mono<Recipient> getRecipient(final TWUserTokens twUserTokens, final Long recipientId) {
        return client.get()
                .uri(recipientByIdPath(recipientId))
                .header(AUTHORIZATION, twUserTokens.bearer())
                .retrieve()
                .bodyToMono(Recipient.class);
    }

    @Override
    public Flux<Recipient> getAllRecipients(final TWUserTokens twUserTokens, final Long twProfileId) {
        return client.get()
                .uri(new UriWithParams(RECIPIENTS_PATH_V2, new ProfileId(twProfileId)))
                .header(AUTHORIZATION, twUserTokens.bearer())
                .retrieve()
                .bodyToFlux(TWRecipientsContent.class)
                .flatMap(content -> fromIterable(content.recipients));

    }

    @Override
    public Mono<String> create(final TWUserTokens twUserTokens, final String requestBody) {
        return client.post()
                .uri(RECIPIENTS_PATH_V1)
                .header(AUTHORIZATION, twUserTokens.bearer())
                .contentType(APPLICATION_JSON_UTF8)
                .body(fromObject(requestBody))
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> getRequirements(TWUserTokens twUserTokens, UUID quoteId) {
        return client.get()
                .uri(recipientRequirementsPath(quoteId))
                .header(AUTHORIZATION, twUserTokens.bearer())
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> createRequirements(TWUserTokens twUserTokens, UUID quoteId, String requestBody) {
        return client.post()
                .uri(recipientRequirementsPath(quoteId))
                .header(AUTHORIZATION, twUserTokens.bearer())
                .contentType(APPLICATION_JSON_UTF8)
                .body(fromObject(requestBody))
                .retrieve()
                .bodyToMono(String.class);
    }

}
