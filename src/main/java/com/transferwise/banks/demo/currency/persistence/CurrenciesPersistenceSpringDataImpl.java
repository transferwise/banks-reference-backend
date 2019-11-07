package com.transferwise.banks.demo.currency.persistence;

import com.transferwise.banks.demo.currency.domain.CurrenciesPersistence;
import com.transferwise.banks.demo.currency.domain.Currency;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
class CurrenciesPersistenceSpringDataImpl implements CurrenciesPersistence {

    private final CurrenciesRepository currenciesRepository;

    CurrenciesPersistenceSpringDataImpl(CurrenciesRepository currenciesRepository) {
        this.currenciesRepository = currenciesRepository;
    }

    @Override
    public List<Currency> findAll() {
        return currenciesRepository.findAll()
                .stream()
                .map(this::mapToCurrency)
                .collect(toList());
    }

    private Currency mapToCurrency(final CurrencyEntity currencyEntity) {
        return new Currency(currencyEntity.getCode(),
                currencyEntity.getName(),
                currencyEntity.getMostPopular(),
                currencyEntity.getCountries());
    }
}
