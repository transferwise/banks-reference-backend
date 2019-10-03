package com.transferwise.t4b.activity;

import com.transferwise.t4b.customer.CustomerTransfer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public Set<CustomerTransfer> getActivities(@RequestParam final Long customerId) {
        return activityService.getActivities(customerId);
    }

}