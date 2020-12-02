# CurrencyExchangeMicroService
## Contents
* [Limits Service](#limits-service)
* [Spring Cloud Config Server](#spring-cloud-config-server)
* [Currency Converter Service](#currency-converter-service)
* [Currency Exchange Service](#currency-exchange-service)
* [Eureka](#eureka)
* [Zuul](#zuul)

## Limits Service
#### Url
'''
http://localhost:8080/limits
'''

#### Summary
Connect to Spring Cloud Config Server, set the property file to activate
from and get properties from the Spring Cloud Config Server

#### Dependencies
* Web
* Devtools
* Actuator
* Config Client
* Hystrix

## Spring Cloud Config Server
#### Url
'''
http://localhost:8888/limits-service/default
http://localhost:8888/limits-service/dev
'''
