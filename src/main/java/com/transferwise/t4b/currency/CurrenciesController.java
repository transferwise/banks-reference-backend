package com.transferwise.t4b.currency;

import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/currencies")
public class CurrenciesController {

    private final CurrenciesRepository currencies;

    public CurrenciesController(final CurrenciesRepository currencies) {
        this.currencies = currencies;
    }

    @GetMapping
    public Publisher<Currency> index() {
        return Flux.fromIterable(currencies.findAll());
    }
}
