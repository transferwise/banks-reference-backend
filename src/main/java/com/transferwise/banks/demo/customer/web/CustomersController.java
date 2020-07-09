package com.transferwise.banks.demo.customer.web;

<<<<<<< HEAD
import com.transferwise.banks.demo.customer.domain.Customer;
import com.transferwise.banks.demo.customer.domain.CustomersService;
=======
import com.transferwise.banks.demo.customer.domain.address.Address;
import com.transferwise.banks.demo.customer.domain.occupation.Occupation;
import com.transferwise.banks.demo.customer.web.occupation.NewOccupationRequest;
import com.transferwise.banks.demo.customer.web.address.NewAddressRequest;
import com.transferwise.banks.demo.customer.domain.Customer;
import com.transferwise.banks.demo.customer.domain.CustomersService;
import io.swagger.annotations.ApiOperation;
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

<<<<<<< HEAD
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
=======
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController(value = "Simulates a bank's customer. Does not act on TransferWise APIs")
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
@RequestMapping("/customers")
public class CustomersController {

    private final CustomersService customersService;

    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }

<<<<<<< HEAD
    @GetMapping
    public Customer show(@RequestParam final Long id) {
        return customersService.find(id);
    }

=======
    @ApiOperation(value = "Get the simulated bank customer's data", notes = "This endpoint is used to retrieve the simulated bank customer's data")
    @GetMapping
    public Customer show(@RequestParam final Long id) throws Exception {
        return customersService.find(id);
    }

    @ApiOperation(value = "Create a simulated bank customer's data", notes = "This endpoint is used to create a simulated bank customer's data")
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Customer create(@Valid @RequestBody final NewCustomerRequest newCustomerRequest) {
        return customersService.save(mapToCustomer(newCustomerRequest));
    }

    private Customer mapToCustomer(final NewCustomerRequest newCustomerRequest) {
        return new Customer(newCustomerRequest.getFirstName(),
                newCustomerRequest.getLastName(),
                newCustomerRequest.getDateOfBirth(),
                newCustomerRequest.getPhoneNumber(),
<<<<<<< HEAD
                newCustomerRequest.getEmail());
=======
                newCustomerRequest.getEmail(),
                mapToAddress(newCustomerRequest.getAddress()));
    }

    private Address mapToAddress(final NewAddressRequest newAddressRequest) {
        return new Address(
                newAddressRequest.getFirstLine(),
                newAddressRequest.getPostCode(),
                newAddressRequest.getCity(),
                newAddressRequest.getState(),
                newAddressRequest.getCountry(),
                mapToOccupations(newAddressRequest.getOccupations())
        );
    }

    private List<Occupation> mapToOccupations(final List<NewOccupationRequest> newOccupationRequestList) {
        List<Occupation> occupations = new ArrayList<>();

        for(NewOccupationRequest newOccupationRequest : newOccupationRequestList ) {
            occupations.add(new Occupation(
                    newOccupationRequest.getCode(),
                    newOccupationRequest.getFormat()
            ));
        }

        return occupations;
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
    }
}
