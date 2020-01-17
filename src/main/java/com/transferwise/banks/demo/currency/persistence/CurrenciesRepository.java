package com.transferwise.banks.demo.currency.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface CurrenciesRepository extends CrudRepository<CurrencyEntity, String> {

    List<CurrencyEntity> findAll();
}
