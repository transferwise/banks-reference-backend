package com.transferwise.banks.demo.quote.twclient;

import com.transferwise.banks.demo.client.params.ProfileId;
import com.transferwise.banks.demo.client.params.TargetAccount;
import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.quote.domain.CreateAnonymousQuote;
import com.transferwise.banks.demo.quote.domain.CreateQuote;
import com.transferwise.banks.demo.quote.domain.Quote;
import com.transferwise.banks.demo.quote.domain.QuotesTWClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.transferwise.banks.demo.client.BodyRequests.forQuoteUpdate;
import static com.transferwise.banks.demo.client.TransferWisePaths.QUOTES_PATH_V2;
import static com.transferwise.banks.demo.client.TransferWisePaths.quotesPathV2;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
class QuotesTWClientImpl implements QuotesTWClient {

    private static final MediaType MERGE_PATCH_JSON = MediaType.valueOf("application/merge-patch+json");

    private final WebClient client;

    QuotesTWClientImpl(WebClient client) {
        this.client = client;
    }


    @Override
    public Mono<Quote> createAnonymousQuote(final CreateAnonymousQuote createAnonymousQuote) {
        return client.post()
                .uri(QUOTES_PATH_V2)
                .contentType(APPLICATION_JSON)
                .body(fromObject(createAnonymousQuote))
                .retrieve()
                .bodyToMono(TWQuoteResponse.class)
                .map(twQuoteResponse -> new Quote(twQuoteResponse.getId(), twQuoteResponse.getSourceCurrency(),
                        twQuoteResponse.getTargetCurrency(),
                        twQuoteResponse.getSourceAmount(),
                        twQuoteResponse.getTargetAmount(),
                        twQuoteResponse.getPayOut(),
                        twQuoteResponse.getRate(),
                        twQuoteResponse.getCreatedTime(),
                        twQuoteResponse.getFee(),
                        twQuoteResponse.getUser(),
                        twQuoteResponse.getProfile(),
                        twQuoteResponse.getRateExpirationTime(),
                        twQuoteResponse.getProvidedAmountType(),
                        twQuoteResponse.getStatus(),
                        twQuoteResponse.getExpirationTime(),
                        twQuoteResponse.getPaymentOptions()));
    }

    @Override
    public Mono<Quote> createQuote(final TWUserTokens twUserTokens, final CreateQuote createQuote) {
        return client.post()
                .uri(QUOTES_PATH_V2)
                .header(AUTHORIZATION, twUserTokens.bearer())
                .contentType(APPLICATION_JSON)
                .body(fromObject(createQuote))
                .retrieve()
                .bodyToMono(TWQuoteResponse.class)
                .map(twQuoteResponse -> new Quote(twQuoteResponse.getId(), twQuoteResponse.getSourceCurrency(),
                        twQuoteResponse.getTargetCurrency(),
                        twQuoteResponse.getSourceAmount(),
                        twQuoteResponse.getTargetAmount(),
                        twQuoteResponse.getPayOut(),
                        twQuoteResponse.getRate(),
                        twQuoteResponse.getCreatedTime(),
                        twQuoteResponse.getFee(),
                        twQuoteResponse.getUser(),
                        twQuoteResponse.getProfile(),
                        twQuoteResponse.getRateExpirationTime(),
                        twQuoteResponse.getProvidedAmountType(),
                        twQuoteResponse.getStatus(),
                        twQuoteResponse.getExpirationTime(),
                        twQuoteResponse.getPaymentOptions()));

    }

    @Override
    public Mono<Quote> updateQuote(final TWUserTokens twUserTokens, final UUID quoteId, final Long profileId, final Long recipientId) {
        return client.patch()
                .uri(quotesPathV2(quoteId))
                .header(AUTHORIZATION, twUserTokens.bearer())
                .contentType(MERGE_PATCH_JSON)
                .body(forQuoteUpdate(new ProfileId(profileId), new TargetAccount(recipientId)))
                .retrieve()
                .bodyToMono(TWQuoteResponse.class)
                .map(twQuoteResponse -> new Quote(twQuoteResponse.getId(), twQuoteResponse.getSourceCurrency(),
                        twQuoteResponse.getTargetCurrency(),
                        twQuoteResponse.getSourceAmount(),
                        twQuoteResponse.getTargetAmount(),
                        twQuoteResponse.getPayOut(),
                        twQuoteResponse.getRate(),
                        twQuoteResponse.getCreatedTime(),
                        twQuoteResponse.getFee(),
                        twQuoteResponse.getUser(),
                        twQuoteResponse.getProfile(),
                        twQuoteResponse.getRateExpirationTime(),
                        twQuoteResponse.getProvidedAmountType(),
                        twQuoteResponse.getStatus(),
                        twQuoteResponse.getExpirationTime(),
                        twQuoteResponse.getPaymentOptions()));

    }

    @Override
    public Mono<Quote> getQuote(TWUserTokens twUserTokens, UUID quoteId) {
        return client.get()
                .uri(quotesPathV2(quoteId))
                .header(AUTHORIZATION, twUserTokens.bearer())
                .retrieve()
                .bodyToMono(TWQuoteResponse.class)
                .map(twQuoteResponse -> new Quote(twQuoteResponse.getId(), twQuoteResponse.getSourceCurrency(),
                        twQuoteResponse.getTargetCurrency(),
                        twQuoteResponse.getSourceAmount(),
                        twQuoteResponse.getTargetAmount(),
                        twQuoteResponse.getPayOut(),
                        twQuoteResponse.getRate(),
                        twQuoteResponse.getCreatedTime(),
                        twQuoteResponse.getFee(),
                        twQuoteResponse.getUser(),
                        twQuoteResponse.getProfile(),
                        twQuoteResponse.getRateExpirationTime(),
                        twQuoteResponse.getProvidedAmountType(),
                        twQuoteResponse.getStatus(),
                        twQuoteResponse.getExpirationTime(),
                        twQuoteResponse.getPaymentOptions()));
    }

}
