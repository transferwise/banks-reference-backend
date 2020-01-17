# Bank Integrations Reference Implementation (Backend)

The reference implementation for designing the international payments experience with the TransferWise API.

This implementation is based on the following documents:

- [TransferWise for Banks API](https://transferwise.github.io/api-docs-banks/#transferwise-for-banks-api)
- [Bank Integrations Design Guide](https://www.notion.so/Bank-Integrations-Design-Guide-8c375c5c5f1e4c64953b4b601ff6abc6)

Have a look at [Bank Integrations Reference Implementation (Android)](https://github.com/transferwise/banks-reference-android) for a reference Android App implementation.

# Overview

The reference backend consists of:
- Main Java Spring Boot application.
- Database that represents the internal database of the Bank.
- A set of unit and integration tests covering a variety of use cases.

### Disclaimer

This repository contains a representation of a Bank's integration with TransferWise's API but this is NOT production-ready code.

## Spring Boot application

The Spring Boot application will provide of a set of endpoints to be consumed by the Bank's Apps. You will find more information about this endpoints once you run the application and checkout the endpoint documentation powered by Swagger that you can find in http://localhost:8080/swagger-ui.html

Before running the server, you'll need to set the following values located in [application.properties](https://github.com/transferwise/banks-reference-backend/blob/master/src/main/resources/application.properties)

- `twbank.clientId` -> unique Id identifying your product (Please contact `banks@transferwise.com` to get started.)
- `twbank.secret` -> secret linked to the previous client id.

## Database

This example database represents the internal database of the Bank and contains the following:
- Bank's customer information
- Supported currencies
- Transfer history
- A correlation between TransferWise and Banks customer IDs
- Token management

# Development setup

## Tech dependencies

In order to run this server, you must have the following dependencies installed in your machine
- Java 11+
- Postgres DB

** We provide a [docker compose file](https://github.com/transferwise/banks-reference-backend/blob/master/docker-compose.yml) that starts up all necessary dependencies with the command
`docker-compose -d up`

## Running the server

Once dependencies are running, to startup the Spring Boot application please run:

`./gradlew bootRun`

## Running the tests

To run the tests please run:

`./gradlew test`

# License
```
Copyright 2019,2020 TransferWise Ltd.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
