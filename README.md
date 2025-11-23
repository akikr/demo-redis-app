# A demo-redis-app

This project provides a simple example of how to use Redis as cache-service in a Spring Boot application.  

## Project Requirements

To build and run this project, you'll need the following:

*   Java 25 or later
*   Maven 3.9.11 or later
*   Docker and Docker Compose (The application uses Docker to run a Redis service, so you'll need to
    have Docker installed and running)

## Dependencies

The project relies on a few key dependencies to function:

*   **Spring Boot**: The core framework for building the application.
*   **Spring Data Redis**: For easy integration with the Redis database.
*   **Spring Web**: To create the RESTful API.
*   **Testcontainers**: For running integration tests with a docker container.

For a complete list of dependencies, please see the `pom.xml` file.

## Getting Started

To get started with the project, you'll need to have the project on your local machine.

### Environment Setup

* The project uses SDKMAN for managing Java and Maven versions.
* Initialize your development environment using **SDKMAN** CLI and sdkman env file [`sdkmanrc`](.sdkmanrc)

```shell
sdk env install
sdk env
```
#### Note: To install SDKMAN refer: [sdkman.io](https://sdkman.io/install)

---

### How to run the application

The application can be run in a few different ways, depending on your preference.

### Running with Maven

The simplest way to run the application is to use the Maven wrapper script included in the project.

```shell
sdk env
./mvnw spring-boot:run
```

This will start the application and the Redis database in docker container using Spring Boot's built-in support for Docker Compose.

### Running with Docker

The project also includes a `compose.yml` to be used by spring-boot docker-compose support to run the application and the Redis service in Docker containers.

### Testing the application

### Run the tests using Maven

To run the tests for the application, you can use the following Maven command:

```shell
sdk env
./mvnw clean test
```

This will execute all the `unit-tests` and `integration-tests` for the application using `test-containers` to spin up a Redis service in a docker container for testing purposes.

## Conclusion

The `demo-redis-app` project is a great starting point for anyone looking to learn how to build a simple Spring Boot application with Redis as cache-service.

## Contributing

Explore the code, run the application, and experiment with the API.  Feel free to contribute to this project!

For questions or issues, please open a GitHub issue or submit a pull request.

Happy coding! ✌️
