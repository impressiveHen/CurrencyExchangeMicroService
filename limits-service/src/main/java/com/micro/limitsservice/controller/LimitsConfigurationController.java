package com.micro.limitsservice.controller;

import com.micro.limitsservice.bean.LimitConfiguration;
import com.micro.limitsservice.config.LimitsServiceConfig;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {

    @Autowired
    private LimitsServiceConfig limitsServiceConfig;

    @GetMapping("/limits")
    public LimitConfiguration retrieveLimitsFromConfigurations() {
        return new LimitConfiguration(
            limitsServiceConfig.getMaximum(),
            limitsServiceConfig.getMinimum()
        );
    }

    @GetMapping("/fault-tolerance-example")
    @HystrixCommand(fallbackMethod="fallbackRetrieveConfiguration")
    public LimitConfiguration retrieveConfiguration() {
        throw new RuntimeException("Not available");
    }

    // return default with hystrix, if a service is down, we can return
    // a default behavior in case of exception
    public LimitConfiguration fallbackRetrieveConfiguration() {
        return new LimitConfiguration(9999, 9);
    }
}
