package com.transferwise.banks.demo.credentials.domain.twprofile;

import com.transferwise.banks.demo.customer.domain.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
class TWProfileComparator {

    public Boolean isTwProfileEqualToCustomer(final TWProfile twProfile, final Customer customer) {
        ProfileDetails profileDetails = twProfile.getProfileDetails();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (profileDetails.getFirstName() != null ? !profileDetails.getFirstName().equals(customer.getFirstName()) : customer.getFirstName() != null) {
            return false;
        }
        if (profileDetails.getLastName() != null ? !profileDetails.getLastName().equals(customer.getLastName()) : customer.getLastName() != null) {
            return false;
        }
        if (profileDetails.getDateOfBirth() != null ? !LocalDate.parse(profileDetails.getDateOfBirth(), formatter).equals(customer.getDateOfBirth()) : customer.getDateOfBirth() != null) {
            return false;
        }
        if (profileDetails.getPhoneNumber() != null ? !profileDetails.getPhoneNumber().equals(customer.getPhoneNumber()) : customer.getPhoneNumber() == null) {
            return false;
        }

        return true;
    }
}
