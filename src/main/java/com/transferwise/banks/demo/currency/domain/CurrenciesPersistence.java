package com.transferwise.banks.demo.currency.domain;

import java.util.List;

public interface CurrenciesPersistence {

    List<Currency> findAll();

}
