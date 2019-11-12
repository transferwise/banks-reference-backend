package com.transferwise.banks.demo.credentials.domain.twprofile;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.customer.domain.Customer;

public interface ProfileService {

    void createPersonalProfile(TWUserTokens twUserTokens, Customer customer);

    void performRefreshCycle(TWUserTokens twUserTokens, Long customerId);
}
