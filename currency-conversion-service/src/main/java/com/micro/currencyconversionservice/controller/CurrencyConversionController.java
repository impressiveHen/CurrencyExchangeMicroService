package com.micro.currencyconversionservice.controller;

import com.micro.currencyconversionservice.entity.CurrencyConversionBean;
import com.micro.currencyconversionservice.service.CurrencyExchangeServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {
    private Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);

    @Autowired
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    // http://localhost:8100/currency-converter/from/USD/to/INR/quantity/1000
    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(
        @PathVariable String from,
        @PathVariable String to,
        @PathVariable BigDecimal quantity) {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        // calling another service
        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
            "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
            CurrencyConversionBean.class,
            uriVariables
        );

        CurrencyConversionBean response = responseEntity.getBody();

        return new CurrencyConversionBean(
            response.getId(),
            from,
            to,
            response.getConversionMultiple(),
            quantity,
            quantity.multiply(response.getConversionMultiple()),
            response.getPort()
        );
    }

    // use openfeign to call rest service
    // http://localhost:8100/currency-converter-feign/from/USD/to/INR/quantity/1000
    // zuul
    // http://localhost:8765/currency-conversion-service/currency-converter-feign/from/USD/to/INR/quantity/1000
    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(
        @PathVariable String from,
        @PathVariable String to,
        @PathVariable BigDecimal quantity) {

        CurrencyConversionBean response = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);

        logger.error("Conversion-service: {}", response);

        return new CurrencyConversionBean(
            response.getId(),
            from,
            to,
            response.getConversionMultiple(),
            quantity,
            quantity.multiply(response.getConversionMultiple()),
            response.getPort()
        );
    }
}
