package com.transferwise.banks.demo.credentials.twclient.twaddress;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.credentials.domain.twaddress.CreateAddress;
import com.transferwise.banks.demo.credentials.domain.twaddress.AddressTWClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.transferwise.banks.demo.client.TransferWisePaths.ADDRESS_PATH;
import static com.transferwise.banks.demo.client.TransferWisePaths.getAddressByIdPath;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class AddressTWClientImpl implements AddressTWClient {

    private final WebClient client;

    public AddressTWClientImpl(WebClient client) { this.client = client; }

    @Override
    public Mono<TWAddressResponse> createAddress(Optional<TWUserTokens> twUserTokens, CreateAddress createAddress) {

        return client.post()
                .uri(ADDRESS_PATH)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, twUserTokens.get().bearer())
                .body(fromObject(createAddress))
                .retrieve()
                .bodyToMono(TWAddressResponse.class)
                .map(twAddressResponse -> twAddressResponse);
    }

    @Override
    public Mono<TWAddressResponse> getAddress(TWUserTokens twUserTokens, Long addressId) {
        return client.get()
                .uri(getAddressByIdPath(addressId))
                .header(AUTHORIZATION, twUserTokens.bearer())
                .retrieve()
                .bodyToMono(TWAddressResponse.class)
                .map(twAddressResponse -> twAddressResponse);
    }
}
