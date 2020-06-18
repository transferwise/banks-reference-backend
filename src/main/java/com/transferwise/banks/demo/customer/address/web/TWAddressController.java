package com.transferwise.banks.demo.customer.address.web;

import com.transferwise.banks.demo.customer.address.domain.twaddress.TWAddress;
import com.transferwise.banks.demo.customer.address.domain.twaddress.TWAddressService;
import com.transferwise.banks.demo.customer.address.twclient.TWAddressResponse;
import io.swagger.annotations.ApiOperation;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController(value = "Manages addresses via TransferWise API")
@RequestMapping("/tw/addresses")
public class TWAddressController {

    private final TWAddressService twAddressService;

    public TWAddressController(TWAddressService twAddressService) { this.twAddressService = twAddressService; }

    @ApiOperation(value = "Get the bank customer's address by Id from TransferWise", notes = "This endpoint is used to retrieve the bank customer's address by Id from TransferWise")
    @RequestMapping(value = "address/{id}", method = RequestMethod.GET)
    public Mono<TWAddressResponse> show(@PathVariable final long id, @RequestParam final long customerId) { return twAddressService.getAddress(id, customerId); }

    @ApiOperation(value = "Save a bank customer's address with TransferWise", notes = "This endpoint is used to save a bank customer's address with TransferWise")
    @PostMapping
    public Publisher<TWAddressResponse> create(@RequestParam final long customerId) {

        return twAddressService.createAddress(customerId);
    }
}
