package com.transferwise.banks.demo.customer.address.occupation.web;

import com.transferwise.banks.demo.customer.address.occupation.domain.Occupation;
import com.transferwise.banks.demo.customer.address.occupation.domain.OccupationService;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController(value = "Simulates a bank's customer's occupation management. Does not act on TransferWise APIs")
@RequestMapping("/customers/addresses/occupations")
public class OccupationController {

    private final OccupationService occupationService;

    public OccupationController(OccupationService occupationService) { this.occupationService = occupationService; }

    @ApiOperation(value = "Get the simulated bank customer's occupations", notes = "This endpoint is used to retrieve the simulated bank customer's occupations")
    @GetMapping
    public List<Occupation> show(@RequestParam final long addressId) { return occupationService.findByAddress(addressId); }

    @ApiOperation(value = "Save a simulated bank customer's occupations for an address", notes = "This endpoint is used to save a simulated bank customer's occupations for an address")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Occupation create(@Valid @RequestBody final NewOccupationRequest newOccupationRequest) {
        return occupationService.save(mapToOccupation(newOccupationRequest));
    }

    private Occupation mapToOccupation(final NewOccupationRequest newOccupationRequest) {
        return new Occupation(
                newOccupationRequest.getCode(),
                newOccupationRequest.getFormat(),
                newOccupationRequest.getAddressId()
        );
    }
}
