package com.transferwise.banks.demo.activity;

import com.transferwise.banks.demo.customer.Customer;
import com.transferwise.banks.demo.customer.CustomerTransfer;
import com.transferwise.banks.demo.customer.CustomerTransferStatus;
import com.transferwise.banks.demo.customer.CustomersRepository;
import com.transferwise.banks.demo.support.Fabricator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ActivitiesServiceTest {

    @Mock
    private CustomersRepository customersRepository;

    @InjectMocks
    private ActivitiesService activitiesService;

    @Test
    public void shouldReturnSortedTransfers() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Long customerId = 123L;
        Customer customer = Fabricator.newCustomer();
        CustomerTransfer customerTransferNow = createCustomerTransfer(now);
        customer.addCustomerTransfer(customerTransferNow);

        CustomerTransfer customerTransferBefore = createCustomerTransfer(now.minusDays(2));
        customer.addCustomerTransfer(customerTransferBefore);

        CustomerTransfer customerTransferAfter = createCustomerTransfer(now.plusDays(1));
        customer.addCustomerTransfer(customerTransferAfter);

        given(customersRepository.find(customerId)).willReturn(customer);

        //when
        List<CustomerTransfer> customerTransfers = activitiesService.getCustomerTransfers(customerId);

        //then
        assertThat(customerTransfers)
                .containsExactly(customerTransferBefore, customerTransferNow, customerTransferAfter);

        List<CustomerTransferStatus> transferStatuses = customerTransfers.get(0).getTransferStatuses();
        assertThat(transferStatuses.get(0).getEventTime()).isEqualTo(now.minusDays(2).minusMinutes(2));
        assertThat(transferStatuses.get(1).getEventTime()).isEqualTo(now.minusDays(2));
        assertThat(transferStatuses.get(2).getEventTime()).isEqualTo(now.minusDays(2).plusMinutes(3));


    }

    private CustomerTransfer createCustomerTransfer(LocalDateTime created) {
        CustomerTransferStatus transferStatusNow = createCustomerTransferStatus(created);
        CustomerTransferStatus transferStatusBefore = createCustomerTransferStatus(created.minusMinutes(2));
        CustomerTransferStatus transferStatusAfter = createCustomerTransferStatus(created.plusMinutes(3));

        return new CustomerTransfer(
                1L,
                1L,
                UUID.randomUUID(),
                "reference",
                BigDecimal.ONE,
                created,
                "GBP",
                BigDecimal.TEN,
                "EUR",
                BigDecimal.TEN,
                UUID.randomUUID(),
                "recipient",
                BigDecimal.ZERO,
                Arrays.asList(transferStatusNow, transferStatusBefore, transferStatusAfter));
    }

    private CustomerTransferStatus createCustomerTransferStatus(LocalDateTime eventTime) {
        return new CustomerTransferStatus(123L, "newState", eventTime);
    }
}