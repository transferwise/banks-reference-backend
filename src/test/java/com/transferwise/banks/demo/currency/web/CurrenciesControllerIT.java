package com.transferwise.banks.demo.currency.web;

import com.google.common.net.HttpHeaders;
import com.transferwise.banks.demo.ServerTest;
import com.transferwise.banks.demo.currency.domain.Currency;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@AutoConfigureWebTestClient
public class CurrenciesControllerIT extends ServerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldReturnCurrencies() {
        //given
        Currency gbp = new Currency("GBP", "Pound Sterling", Boolean.TRUE, Collections.singletonList("UK"));
        Currency eur = new Currency("EUR", "Euro", Boolean.TRUE, Arrays.asList("Belgium", "Portugal"));
        Currency usd = new Currency("USD", "United States Dollar", Boolean.TRUE, Collections.singletonList("United States of America"));

        //when & then
        webTestClient.get()
                .uri("/currencies")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Currency.class)
                .consumeWith(listEntityExchangeResult -> {
                    List<Currency> currencies = listEntityExchangeResult.getResponseBody();
                    assertThat(currencies).hasSize(3);
                    assertThat(currencies)
                            .usingFieldByFieldElementComparator()
                            .containsExactlyInAnyOrder(gbp, eur, usd);
                });
    }

}