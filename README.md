# Dictionary Service
This repository contains a Java Spring Boot service designed to run alongside the dictionaries Docker image. It provides two REST endpoints:

* translations between English ⇄ German and Spanish ⇄ German 
* a “Word of the Day” feature available in Spanish, English, and German

# Running the Service

The service requires a PostgreSQL database with the same schema and data structure as the one provided by the [dictionaries](https://github.com/trinimon/dictionaries) project.

In addition, the following environment variables must be configured:

```env
SPRING_DATASOURCE_URL=<JDBC url>
SPRING_DATASOURCE_USERNAME=<translator username>
SPRING_DATASOURCE_PASSWORD=<translator password>
```

In order to enable security use the profile `secured` and add: 

```env
KEYCLOAK_ISSUER_URI=<scheme>://<host>:<port>/realms/<realm>
OAUTH2_AUDIENCE=<audience>
```

CORS can be configured for instance by:

```env
CORS_ENABLED=true
CORS_ALLOWED_ORIGINS=http://<hostname>:<port>,http://<ip>:<port>
```

# Running with Docker

Running in Docker with security and CORS disabled. 

```bash
docker run -p 8080:8080 \
    -e SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/dictionaries \
    -e SPRING_DATASOURCE_USERNAME=translator \
    -e SPRING_DATASOURCE_PASSWORD=secret \
       trinimon/dictionary-service
```

# Docker Hub

For more information, visit the Docker Hub repository [trinimon/dictionary-service](https://hub.docker.com/r/trinimon/dictionary-service) 

# Architecture

The service follows a Ports and Adapters (Hexagonal Architecture) approach to keep the domain logic independent from infrastructure concerns such as persistence and REST APIs.

# Technologies

* Java 25
* Spring Boot 4
* PostgreSQL
* Docker
