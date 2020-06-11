package com.transferwise.banks.demo.customer.address.web;

import com.transferwise.banks.demo.customer.address.domain.Address;
import com.transferwise.banks.demo.customer.address.domain.AddressService;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController(value = "Simulates a bank's customer's address management. Does not act on TransferWise APIs")
@RequestMapping("/customers/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) { this.addressService = addressService; }

    @ApiOperation(value = "Get the simulated bank customer's address by Id", notes = "This endpoint is used to retrieve the simulated bank customer's address by Id")
    @RequestMapping(value = "address", method = RequestMethod.GET)
    public Address show(@RequestParam final long id) { return addressService.find(id); }

    @ApiOperation(value = "Get the simulated bank customer's address by customerId", notes = "This endpoint is used to retrieve the simulated bank customer's address by customerId")
    @GetMapping
    public Address showAddress(@RequestParam final long customerId) { return addressService.findAddress(customerId); }

    @ApiOperation(value = "Save a simulated bank customer's address", notes = "This endpoint is used to save a simulated bank customer's address")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Address create(@Valid @RequestBody final NewAddressRequest newAddressRequest) {
        return addressService.save(mapToCustomerAddress(newAddressRequest));
    }

    private Address mapToCustomerAddress(final NewAddressRequest newAddressRequest) {
        return new Address(
                newAddressRequest.getFirstLine(),
                newAddressRequest.getPostCode(),
                newAddressRequest.getCity(),
                newAddressRequest.getState(),
                newAddressRequest.getCountry(),
                newAddressRequest.getCustomerId()
        );
    }
}
