package com.transferwise.banks.demo.transfer.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface CustomerTransferRepository extends CrudRepository<CustomerTransferEntity, Long> {

    List<CustomerTransferEntity> findAllByCustomerId(Long customerId);

}
