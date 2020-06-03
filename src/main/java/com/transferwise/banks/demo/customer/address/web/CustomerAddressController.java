package com.transferwise.banks.demo.customer.address.web;

import com.transferwise.banks.demo.customer.address.domain.CustomerAddress;
import com.transferwise.banks.demo.customer.address.domain.CustomerAddressService;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController(value = "Simulates a bank's customer's address management. Does not act on TransferWise APIs")
@RequestMapping("/customers/addresses")
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    public CustomerAddressController(CustomerAddressService customerAddressService) { this.customerAddressService = customerAddressService; }

    @ApiOperation(value = "Get the simulated bank customer's address by Id", notes = "This endpoint is used to retrieve the simulated bank customer's address by Id")
    @RequestMapping(value = "address", method = RequestMethod.GET)
    public CustomerAddress show(@RequestParam final long id) { return customerAddressService.find(id); }

    @ApiOperation(value = "Get the simulated bank customer's address by customerId", notes = "This endpoint is used to retrieve the simulated bank customer's address by customerId")
    @GetMapping
    public CustomerAddress showAddress(@RequestParam final long customerId) { return customerAddressService.findAddress(customerId); }

    @ApiOperation(value = "Save a simulated bank customer's address", notes = "This endpoint is used to save a simulated bank customer's address")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public CustomerAddress create(@Valid @RequestBody final NewCustomerAddressRequest newCustomerAddressRequest) {
        return customerAddressService.save(mapToCustomerAddress(newCustomerAddressRequest));
    }

    private CustomerAddress mapToCustomerAddress(final NewCustomerAddressRequest newCustomerAddressRequest) {
        return new CustomerAddress(
                newCustomerAddressRequest.getFirstLine(),
                newCustomerAddressRequest.getPostCode(),
                newCustomerAddressRequest.getCity(),
                newCustomerAddressRequest.getState(),
                newCustomerAddressRequest.getCountry(),
                newCustomerAddressRequest.getCustomerId()
        );
    }
}
