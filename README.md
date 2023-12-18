# shortener

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run application in dev mode using:
```shell script
./mvnw compile quarkus:dev
```

This however will skip tests. To run tests and then dev mode:
```shell script
./mvnw package quarkus:dev
```

Example of POST request for simple testing, where:
-X specifies the method;
-H stands for headers;
-d stands for data
```shell script
curl -X 'POST' \
  'http://localhost:8081/urls/aliases' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "url": "https://thorben-janssen.com/naming-strategies-in-hibernate-5/#names-in-snake-case-instead-of-camel-case"
}'
```

To check redirection, insert aliasFullUrl from response:
```shell script
curl -X GET '<aliasFullUrl from POST response>' -H 'accept: */*' -i
```

Swagger Open API available only in dev mode and accessible on http://localhost:8081/q/swagger-ui

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8081/q/dev/.