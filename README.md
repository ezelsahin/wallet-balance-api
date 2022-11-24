# WALLET BALANCE API

A wallet balance change API. It can be used for to make balance changes for a client API. It can get request for 
deposit and withdrawal transactions for customers wallets. It validates if requests are valid and completes the 
transactions. Also a customers current wallet balance quarry can be placed with the API. And it is possible to get 
all transactions of an account. Wallet Balance API is running on the JVM.

## Description
A Rest API to access monetary accounts with the current wallet balance of a customer. The wallet balance can be 
modified by transaction requests. Transaction request could be two different type: Deposit transactions for adding 
funds or withdrawal transactions for removing funds.

There are some conditions for a request to be placed:
- All transaction requests must have a unique transaction Id
- The customer Id in all requests must be already exist in the wallet balance database
- For withdrawal requests wallet balance must be grater than or equal to the requested amount 
 (wallet balance - requested withdrawal amount >= 0)
- New customers who will be added to the wallet balance table must have unique Id's   

## Api requirements and running instructions
1. Java 11
2. Maven 3 to build the application
3. H2 Memory Database
4. From the root folder of the application run:
``` 
mvn clean install
``` 
5. Start application by:
``` 
mvn spring-boot:run
``` 
All tables will be created on H2 database. There are some dummy data for to be used in data.sql file

``` 
java -jar target/wallet-balance-api-1.0-SNAPSHOT.jar
``` 
6. To check that application started successfully go to:
``` 
http://localhost:8080/test
``` 
You should see:
``` 
Testing is successful!
``` 
The port can be also configured from 
``` 
wallet-balance-api/src/main/resources/application.properties
```
see 'server.port' property

## API endpoints
Examples of all endpoints can be found and can be tested in:
``` 
http://localhost:8080/swagger-ui/index.html?configUrl=/wallet-app/swagger-config
``` 

## Technology used

- Spring Boot, including Spring Data JPA for JPA based repositories
- REST
- H2 In-memory Database
- Lombok
- Swagger
- JUnit, Mockito

## Support of the aspects:

1. Transactions on service and repository level ensure atomicity
2. Identifiers for entities and primary keys in the db ensure idempotency. As well as unique transaction Id's 
for wallet transactions
3. Scalability: 
Application instances can be running stateless. This can be solved by installing wallet balance 
API on several hosts, with database server running on it own host/hosts(can be distributed).
Load balancer will share requests between the application instances and provide high-availability.
4. Concurrency:
Application is thread safe and can be requested from multiple sources.
In the application there are no shared objects, so there should not be concurrency issues.

 








