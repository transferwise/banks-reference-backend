package com.transferwise.banks.demo.currency;

import org.springframework.data.repository.CrudRepository;

public interface CurrenciesRepository extends CrudRepository<Currency, String> {
}
