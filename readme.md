<h1 align="center">  Azercell Fintech Task </h1> <br>

## Table of Contents

- [Introduction](#Introduction)
- [Database schema](#Database-schema)
- [Tech stack](#Tech-stack)

## Archtecuture

![](images/azercell%20fintech%20architecture.png)

## Introduction
In this project use Choreography Saga Pattern
There are 7 microservices 
1. **gateway**: in this microservice is gateway and its responsibility is route all 
determines url to microservice and additionally it check authentication if token is exist
2.  **auth-ms**: in this microservice is authentication service and it responsibility is authenticate customer
3.  **customer-ms**: in this microservice stores customer info
4.  **otp-ms**: this microservice generate otp and check it and send it email microservice 
5.  **email-service**: this microservice send mail to target mail
6.  **payment-ms**: this microservice make transaction and store transaction info and also make sure this info
synchrony with customer data

## Database schema

1. **App name:** customer-ms 
   ![](images/customer%20db.png)
2.  **App name:** payment-ms
![](images/payment%20db.png)

## Tech stack

1. Spring Boot
2. Java 17
3. RabbitMQ
4. PostgreSQL
5. Redis
6. Eureka Service Discovery
