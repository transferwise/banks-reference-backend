package com.transferwise.banks.demo.activity;

import com.transferwise.banks.demo.customer.CustomerTransfer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("activities")
public class ActivitiesController {

    private final ActivitiesService activitiesService;

    public ActivitiesController(ActivitiesService activitiesService) {
        this.activitiesService = activitiesService;
    }


    @GetMapping
    public List<CustomerTransfer> getActivities(@RequestParam final Long customerId) {
        return activitiesService.getCustomerTransfers(customerId);
    }

}