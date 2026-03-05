# A demo-redis-app

This project is a Spring Boot application demonstrating Redis-backed caching and correlation-store patterns.

## Project Requirements

To build and run this project, you'll need:

- Java 25 or later
- Maven 3.9.11 or later
- Docker + Docker Compose

## Dependencies

Main dependencies:

- **Spring Boot**
- **Spring Data Redis**
- **Spring Web**
- **Testcontainers** (integration tests)

For complete details, see [`pom.xml`](pom.xml).

## Environment Setup

- The project uses SDKMAN for Java/Maven version management.
- Initialize the local toolchain using [`.sdkmanrc`](.sdkmanrc):

```shell
sdk env install
sdk env
```

#### Note: To install SDKMAN refer: [sdkman.io](https://sdkman.io/install)

## Development Run

Run with Maven:

```shell
sdk env
./mvnw spring-boot:run
```

By default, development mode uses `compose.yml` through Spring Boot Docker Compose integration.

## Prod Profile (Redis Sentinel)

This repository now includes production Redis Sentinel topology and app profile files:

- [`compose-prod.yml`](compose-prod.yml): Redis master + 2 replicas + 3 Sentinel nodes
- [`src/main/resources/application-prod.properties`](src/main/resources/application-prod.properties): Spring Redis Sentinel configuration

### Sentinel topology summary

- `redis-master` (primary)
- `redis-replica-1`, `redis-replica-2` (replicas)
- `redis-sentinel-1`, `redis-sentinel-2`, `redis-sentinel-3` (quorum-based failover)
- Sentinel master name: `mymaster`

### Required environment variables

At minimum, set:

```shell
export REDIS_PASSWORD='change_me'
```

Optional overrides:

```shell
export REDIS_SENTINEL_MASTER='mymaster'
export REDIS_SENTINEL_NODES='redis-sentinel-1:26379,redis-sentinel-2:26379,redis-sentinel-3:26379'
export APP_REDIS_DEFAULT_TTL_VALUE='30'
export APP_REDIS_CORRELATION_TTL_VALUE='3000'
```

## How To Run In Production Setup

### 1. Start Redis + Sentinel stack

```shell
REDIS_PASSWORD='change_me' docker compose -f compose-prod.yml up -d
```

### 2. Run app as container (recommended with this topology)

Build image:

```shell
./mvnw clean package spring-boot:build-image-no-fork -DskipTests
```

Start app profile from compose:

```shell
REDIS_PASSWORD='change_me' docker compose -f compose-prod.yml --profile app up -d
```

### 3. Basic health checks

```shell
docker compose -f compose-prod.yml ps
docker compose -f compose-prod.yml logs redis-sentinel-1 --tail=50
curl -s http://localhost:8090/app/actuator/health | jq
```

## Testing

Run unit + integration tests:

```shell
sdk env
./mvnw clean test
```

## Contributing

Explore the code, run the application, and experiment with the API.  
For questions or issues, open a GitHub issue or submit a pull request.

Happy coding! ✌️
