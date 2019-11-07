package com.transferwise.banks.demo.currency.web;

import com.transferwise.banks.demo.currency.domain.CurrenciesService;
import com.transferwise.banks.demo.currency.domain.Currency;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/currencies")
public class CurrenciesController {

    private final CurrenciesService currenciesService;

    public CurrenciesController(CurrenciesService currenciesService) {
        this.currenciesService = currenciesService;
    }

    @GetMapping
    public Publisher<Currency> index() {
        return Flux.fromIterable(currenciesService.findAll());
    }
}
