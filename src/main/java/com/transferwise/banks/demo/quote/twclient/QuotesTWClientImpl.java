package com.transferwise.banks.demo.quote.twclient;

import com.transferwise.banks.demo.client.params.Parameter;
import com.transferwise.banks.demo.client.params.ProfileId;
import com.transferwise.banks.demo.client.params.TargetAccount;
import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.quote.domain.CreateAnonymousQuote;
import com.transferwise.banks.demo.quote.domain.CreateQuote;
import com.transferwise.banks.demo.quote.domain.Quote;
import com.transferwise.banks.demo.quote.domain.QuotesTWClient;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import static com.transferwise.banks.demo.client.TransferWisePaths.QUOTES_PATH_V2;
import static com.transferwise.banks.demo.client.TransferWisePaths.quotesPathV2;
import static java.util.stream.Collectors.toMap;
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
        //TODO do this mapping?
        //TWCreateQuoteRequest twCreateQuoteRequest = quotesMapperTWClient.mapToTWCreateQuoteRequest(createQuote);

        return client.post()
                .uri(QUOTES_PATH_V2)
                .contentType(APPLICATION_JSON)
                .body(fromObject(createAnonymousQuote))
                .retrieve()
                .bodyToMono(Quote.class);
    }

    @Override
    public Mono<Quote> createQuote(final TWUserTokens twUserTokens, final CreateQuote createQuote) {
        return client.post()
                .uri(QUOTES_PATH_V2)
                .header(AUTHORIZATION, twUserTokens.bearer())
                .contentType(APPLICATION_JSON)
                .body(fromObject(createQuote))
                .retrieve()
                .bodyToMono(Quote.class);

    }

    @Override
    public Mono<Quote> updateQuote(final TWUserTokens twUserTokens, final UUID quoteId, final Long profileId, final TargetAccount targetAccount) {
        return client.patch()
                .uri(quotesPathV2(quoteId))
                .header(AUTHORIZATION, twUserTokens.bearer())
                .contentType(MERGE_PATCH_JSON)
                .body(forQuoteUpdate(new ProfileId(profileId), targetAccount))
                .retrieve()
                .bodyToMono(Quote.class);

    }

    @Override
    public Mono<Quote> getQuote(TWUserTokens twUserTokens, UUID quoteUuid) {
        return client.get()
                .uri(quotesPathV2(quoteUuid))
                .header(AUTHORIZATION, twUserTokens.bearer())
                .retrieve()
                .bodyToMono(Quote.class);
    }

    private BodyInserter<Map<String, String>, ReactiveHttpOutputMessage> forQuoteUpdate(final ProfileId profileId, final TargetAccount targetAccount) {
        return fromObject(map(profileId, targetAccount));
    }

    private static Map<String, String> map(final Parameter... parameters) {
        return Arrays.stream(parameters).collect(toMap(Parameter::key, Parameter::value));
    }
}
