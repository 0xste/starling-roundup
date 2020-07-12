# starling-roundup

![Java CI with Gradle](https://github.com/stefanomantini/starling-roundup/workflows/Java%20CI%20with%20Gradle/badge.svg)

### Specification
For a customer, take all the transactions in a given week and round them up to the
nearest pound. For example with spending of £4.35, £5.20 and £0.87, the round-up
would be £1.58. This amount should then be transferred into a savings goal, helping the
customer save for future adventures.

### Note
- Project is built and compiled on openjdk14 and run on openJDK JRE
- Uses googleJavaFormatter plugin (invalid formatting will fail pipeline)
- Uses Karate for cucumber-ish integration tests in the API tier
- Uses springfox to autogenerate swagger docs
- Various configs can be set, refer to src/main/resources/application.yml for detail
- Basic auth is disabled unless ENABLE_BASIC_AUTH is set

##### Roundup Transactions between 'transactionTimes' and add 'amount' to an accounts given savings goal
```
curl -X PUT 'http://localhost:8080/api/v1/account/0f626e6b-d357-4a35-8219-c4e62df5f326/round-up/e5ed27ef-6bc5-45fd-adf8-2697c4cad02b?fromDate=2020-07-12T20:17:40.122Z&toDate=2020-07-12T20:19:40.122Z'
```

### Getting started

###### Swagger Docs available at:
```
# Start service
./gradlew bootRun
# raw json
curl -X GET http://localhost:8080/v2/api-docs
# SwaggerUI
http://localhost:8080/swagger-ui.html
```

###### Running the spring boot app using the gradle wrapper
```
./gradlew bootRun
```

###### Running unit tests
```
./gradlew test --tests com.stefanomantini.starlingroundup
```

###### Running integration tests (API) -> some manual tweaks required to make them pass, would need to create transactions as part of the tests to properly test this
```
./gradlew bootRun
./gradlew test --tests karate
```

###### Building the runnable tomcat jar
```
./gradlew clean build
```

###### Running interactively with docker
```
./gradlew clean build
docker build -t starling-roundup .
docker run -i starling-roundup -p 8080:8080
```

###### For further info, see: .github/workflows/gradle.yml & the github actions pipeline