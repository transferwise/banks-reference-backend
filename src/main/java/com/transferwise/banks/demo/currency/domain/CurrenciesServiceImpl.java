package com.transferwise.banks.demo.currency.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
class CurrenciesServiceImpl implements CurrenciesService {

    private final CurrenciesPersistence currenciesPersistence;

    CurrenciesServiceImpl(CurrenciesPersistence currenciesPersistence) {
        this.currenciesPersistence = currenciesPersistence;
    }

    @Override
    public List<Currency> findAll() {
        return currenciesPersistence.findAll();
    }
}
