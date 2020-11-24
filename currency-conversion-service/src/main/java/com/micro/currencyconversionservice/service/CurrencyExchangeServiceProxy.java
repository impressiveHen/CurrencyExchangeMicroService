package com.micro.currencyconversionservice.service;

import com.micro.currencyconversionservice.entity.CurrencyConversionBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// service you want to call
//1. hard code localhost:8000 single currency-exchange-service instance
//@FeignClient(name="currency-exchange-service", url="localhost:8000")
//2. eureka name service to get service
//@FeignClient(name="currency-exchange-service")
//3. go through zuul api gate way when sending to currency-exchange-service
@FeignClient(name="netflix-zuul-api-gateway-server")
// use ribbon to distribute load between multiple currency-exchange-service instances
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeServiceProxy {
    // @GetMapping("/currency-exchange/from/{from}/to/{to}")
    // zuul endpoint
    @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    CurrencyConversionBean retrieveExchangeValue(
        @PathVariable("from") String from,
        @PathVariable("to") String to);
}