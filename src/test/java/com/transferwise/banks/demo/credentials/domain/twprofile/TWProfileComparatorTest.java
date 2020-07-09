package com.transferwise.banks.demo.credentials.domain.twprofile;

import com.transferwise.banks.demo.customer.domain.Customer;
<<<<<<< HEAD
=======
import com.transferwise.banks.demo.customer.domain.address.Address;
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
import org.junit.Test;

import java.time.LocalDate;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

public class TWProfileComparatorTest {

    private TWProfileComparator twProfileComparator = new TWProfileComparator();

    @Test
    public void shouldReturnTrueWhenEqualProfileAndCustomerData() {
        //given
        Long twProfileId = 1L;
        Long customerId = 2L;

        String type = "personal";
        String firstName = "first";
        String lastName = "last";
        String profileDateOfBirth = "1970-02-01";
        LocalDate customerDateOfBirth = LocalDate.of(1970, 2, 1);
        String phoneNumber = "+4412345678";

        String email = "email@tw.com";

<<<<<<< HEAD
        ProfileDetails profileDetails = new ProfileDetails(firstName, lastName, profileDateOfBirth, phoneNumber);
        TWProfile twProfile = new TWProfile(twProfileId, customerId, type, profileDetails, now());

        Customer customer = new Customer(customerId, firstName, lastName, customerDateOfBirth, phoneNumber, email);
=======
        Address address = new Address(1L,
                "56 Shoreditch High Street",
                "E1 6JJ",
                "London",
                "",
                "GB");

        ProfileDetails profileDetails = new ProfileDetails(firstName, lastName, profileDateOfBirth, phoneNumber);
        TWProfile twProfile = new TWProfile(twProfileId, customerId, type, profileDetails, now());

        Customer customer = new Customer(customerId, firstName, lastName, customerDateOfBirth, phoneNumber, email, address);
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc

        //when
        Boolean result = twProfileComparator.isTwProfileEqualToCustomer(twProfile, customer);

        //then
        assertThat(result).isTrue();
    }


    @Test
    public void shouldReturnFalseWhenDifferentFirstName() {
        //given
        Long twProfileId = 1L;
        Long customerId = 2L;

        String type = "personal";
        String profileFirstName = "first";
        String customerFirstName = "customerfirst";
        String lastName = "last";
        String profileDateOfBirth = "1970-02-01";
        LocalDate customerDateOfBirth = LocalDate.of(1970, 2, 1);
        String phoneNumber = "+4412345678";

        String email = "email@tw.com";

<<<<<<< HEAD
        ProfileDetails profileDetails = new ProfileDetails(profileFirstName, lastName, profileDateOfBirth, phoneNumber);
        TWProfile twProfile = new TWProfile(twProfileId, customerId, type, profileDetails, now());

        Customer customer = new Customer(customerId, customerFirstName, lastName, customerDateOfBirth, phoneNumber, email);
=======
        Address address = new Address(1L,
                "56 Shoreditch High Street",
                "E1 6JJ",
                "London",
                "",
                "GB");

        ProfileDetails profileDetails = new ProfileDetails(profileFirstName, lastName, profileDateOfBirth, phoneNumber);
        TWProfile twProfile = new TWProfile(twProfileId, customerId, type, profileDetails, now());

        Customer customer = new Customer(customerId, customerFirstName, lastName, customerDateOfBirth, phoneNumber, email, address);
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc

        //when
        Boolean result = twProfileComparator.isTwProfileEqualToCustomer(twProfile, customer);

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnFalseWhenDifferentLastName() {
        //given
        Long twProfileId = 1L;
        Long customerId = 2L;

        String type = "personal";
        String firstName = "first";
        String profileLastName = "last";
        String customerLastName = "customerlast";
        String profileDateOfBirth = "1970-02-01";
        LocalDate customerDateOfBirth = LocalDate.of(1970, 2, 1);
        String phoneNumber = "+4412345678";

        String email = "email@tw.com";

<<<<<<< HEAD
        ProfileDetails profileDetails = new ProfileDetails(firstName, profileLastName, profileDateOfBirth, phoneNumber);
        TWProfile twProfile = new TWProfile(twProfileId, customerId, type, profileDetails, now());

        Customer customer = new Customer(customerId, firstName, customerLastName, customerDateOfBirth, phoneNumber, email);
=======
        Address address = new Address(1L,
                "56 Shoreditch High Street",
                "E1 6JJ",
                "London",
                "",
                "GB");

        ProfileDetails profileDetails = new ProfileDetails(firstName, profileLastName, profileDateOfBirth, phoneNumber);
        TWProfile twProfile = new TWProfile(twProfileId, customerId, type, profileDetails, now());

        Customer customer = new Customer(customerId, firstName, customerLastName, customerDateOfBirth, phoneNumber, email, address);
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc

        //when
        Boolean result = twProfileComparator.isTwProfileEqualToCustomer(twProfile, customer);

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnFalseWhenDifferentDateOfBirth() {
        //given
        Long twProfileId = 1L;
        Long customerId = 2L;

        String type = "personal";
        String firstName = "first";
        String lastName = "last";
        String profileDateOfBirth = "1970-04-03";
        LocalDate customerDateOfBirth = LocalDate.of(1970, 2, 1);
        String phoneNumber = "+4412345678";

        String email = "email@tw.com";

<<<<<<< HEAD
        ProfileDetails profileDetails = new ProfileDetails(firstName, lastName, profileDateOfBirth, phoneNumber);
        TWProfile twProfile = new TWProfile(twProfileId, customerId, type, profileDetails, now());

        Customer customer = new Customer(customerId, firstName, lastName, customerDateOfBirth, phoneNumber, email);
=======
        Address address = new Address(1L,
                "56 Shoreditch High Street",
                "E1 6JJ",
                "London",
                "",
                "GB");

        ProfileDetails profileDetails = new ProfileDetails(firstName, lastName, profileDateOfBirth, phoneNumber);
        TWProfile twProfile = new TWProfile(twProfileId, customerId, type, profileDetails, now());

        Customer customer = new Customer(customerId, firstName, lastName, customerDateOfBirth, phoneNumber, email, address);
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc

        //when
        Boolean result = twProfileComparator.isTwProfileEqualToCustomer(twProfile, customer);

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnFalseWhenDifferentPhoneNumber() {
        //given
        Long twProfileId = 1L;
        Long customerId = 2L;

        String type = "personal";
        String firstName = "first";
        String lastName = "last";
        String profileDateOfBirth = "1970-02-01";
        LocalDate customerDateOfBirth = LocalDate.of(1970, 2, 1);
        String profilePhoneNumber = "+4412345678";
        String customerPhoneNumber = "+3312345678";

        String email = "email@tw.com";

<<<<<<< HEAD
        ProfileDetails profileDetails = new ProfileDetails(firstName, lastName, profileDateOfBirth, profilePhoneNumber);
        TWProfile twProfile = new TWProfile(twProfileId, customerId, type, profileDetails, now());

        Customer customer = new Customer(customerId, firstName, lastName, customerDateOfBirth, customerPhoneNumber, email);
=======
        Address address = new Address(1L,
                "56 Shoreditch High Street",
                "E1 6JJ",
                "London",
                "",
                "GB");

        ProfileDetails profileDetails = new ProfileDetails(firstName, lastName, profileDateOfBirth, profilePhoneNumber);
        TWProfile twProfile = new TWProfile(twProfileId, customerId, type, profileDetails, now());

        Customer customer = new Customer(customerId, firstName, lastName, customerDateOfBirth, customerPhoneNumber, email, address);
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc

        //when
        Boolean result = twProfileComparator.isTwProfileEqualToCustomer(twProfile, customer);

        //then
        assertThat(result).isFalse();
    }

}