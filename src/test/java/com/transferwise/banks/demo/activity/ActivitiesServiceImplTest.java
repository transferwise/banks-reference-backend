package com.transferwise.banks.demo.activity;

import com.transferwise.banks.demo.activity.domain.ActivitiesServiceImpl;
import com.transferwise.banks.demo.customer.persistence.CustomerEntity;
import com.transferwise.banks.demo.transfer.persistence.CustomerTransferEntity;
import com.transferwise.banks.demo.transfer.persistence.CustomerTransferStatusEntity;
import com.transferwise.banks.demo.customer.persistence.CustomersRepository;
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
public class ActivitiesServiceImplTest {

    @Mock
    private CustomersRepository customersRepository;

    @InjectMocks
    private ActivitiesServiceImpl activitiesServiceImpl;

    @Test
    public void shouldReturnSortedTransfers() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Long customerId = 123L;
        CustomerEntity customerEntity = Fabricator.newCustomer();
        CustomerTransferEntity customerTransferEntityNow = createCustomerTransfer(now);
        customerEntity.addCustomerTransfer(customerTransferEntityNow);

        CustomerTransferEntity customerTransferEntityBefore = createCustomerTransfer(now.minusDays(2));
        customerEntity.addCustomerTransfer(customerTransferEntityBefore);

        CustomerTransferEntity customerTransferEntityAfter = createCustomerTransfer(now.plusDays(1));
        customerEntity.addCustomerTransfer(customerTransferEntityAfter);

        given(customersRepository.find(customerId)).willReturn(customerEntity);

        //when
        List<CustomerTransferEntity> customerTransferEntities = activitiesServiceImpl.getCustomerTransfers(customerId);

        //then
        assertThat(customerTransferEntities)
                .containsExactly(customerTransferEntityBefore, customerTransferEntityNow, customerTransferEntityAfter);

        List<CustomerTransferStatusEntity> transferStatuses = customerTransferEntities.get(0).getTransferStatuses();
        assertThat(transferStatuses.get(0).getEventTime()).isEqualTo(now.minusDays(2).minusMinutes(2));
        assertThat(transferStatuses.get(1).getEventTime()).isEqualTo(now.minusDays(2));
        assertThat(transferStatuses.get(2).getEventTime()).isEqualTo(now.minusDays(2).plusMinutes(3));


    }

    private CustomerTransferEntity createCustomerTransfer(LocalDateTime created) {
        CustomerTransferStatusEntity transferStatusNow = createCustomerTransferStatus(created);
        CustomerTransferStatusEntity transferStatusBefore = createCustomerTransferStatus(created.minusMinutes(2));
        CustomerTransferStatusEntity transferStatusAfter = createCustomerTransferStatus(created.plusMinutes(3));

        return new CustomerTransferEntity(
                1L,
                customerId, 1L,
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

    private CustomerTransferStatusEntity createCustomerTransferStatus(LocalDateTime eventTime) {
        return new CustomerTransferStatusEntity(123L, "newState", eventTime);
    }
}