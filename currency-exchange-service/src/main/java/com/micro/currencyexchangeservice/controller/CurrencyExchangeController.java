package com.micro.currencyexchangeservice.controller;

import com.micro.currencyexchangeservice.entry.ExchangeValue;
import com.micro.currencyexchangeservice.service.ExchangeValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {
    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    //    Environment is an interface representing the environment in which the current application is running. It can be use to get profiles and properties of the application environment.
    @Autowired
    private Environment environment;

    @Autowired
    private ExchangeValueRepository exchangeValueRepository;

    // http://localhost:8001/currency-exchange/from/USD/to/INR
    // zuul
    // http://localhost:8765/currency-exchange-service/currency-exchange/from/USD/to/INR
    @GetMapping("currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable String from,
                                               @PathVariable String to) {

        ExchangeValue exchangeValue = exchangeValueRepository.findByFromAndTo(from, to);

        exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));

        logger.error("Exchange-service: {}", exchangeValue);

        return exchangeValue;
    }
}
