## Java Monolith Example

## About The Project

- *Remember this project is not vibe coded. Each line of code is write by a human.*

### Built With

* [![Spring Boot][Spring-badge]][Spring-url]
* [![Java][Java-badge]][Java-url]
* [![Maven][Maven-badge]][Maven-url]
* [![Docker][Docker-badge]][Docker-url]
* [![Mockito][Mockito-badge]][Mockito-url]
* [![JUnit5][JUnit5-badge]][JUnit5-url]
* [![PostgreSQL][Postgres-badge]][Postgres-url]
* [![H2 Database][H2-badge]][H2-url]

<!-- MARKDOWN LINK & IMAGES -->
[Spring-badge]: https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white
[Spring-url]: https://spring.io/projects/spring-boot
[Java-badge]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://www.oracle.com/java/
[Maven-badge]: https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white
[Maven-url]: https://maven.apache.org/
[Docker-badge]: https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com/
[Mockito-badge]: https://img.shields.io/badge/Mockito-78A3AD?style=for-the-badge&logo=mockito&logoColor=white
[Mockito-url]: https://site.mockito.org/
[JUnit5-badge]: https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white
[JUnit5-url]: https://junit.org/junit5/
[Postgres-badge]: https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white
[Postgres-url]: https://www.postgresql.org/
[H2-badge]: https://img.shields.io/badge/H2%20Database-004B8D?style=for-the-badge&logo=databricks&logoColor=white
[H2-url]: http://www.h2database.com/

## Getting Started

To install this project, you will need to have Java 17 or latest version installed on your machine. You can download it from the official website.

Once you have Java 17 installed, you can use to 

### Setup

This project was created by Amman Rizwan and is available on GitHub. If you're interested in contributing to this project, please follow these guidelines and best practices.

### Prerequisites

- **Java 17**
  ```bash
  sudo apt update
  sudo apt upgrade
  sudo apt install java
  javac --version
  java --version
  ```

- **Maven (Optional)**
  ```bash
  sudo apt update
  sudo apt install maven
  
  mvn --version
  ```
- **Git**
  ```bash
  sudo apt update
  sudo apt install git
  
  git --version
  ```

### Configure Project

The project is designed to be flexible and customizable. You can modify the `src` folder to include new files or modify existing ones to suit your needs.

To configure the project, you will need to create the `.env` and `.env.test` file located in the root directory.

*.env file*
```.env
DB_URL=
DB_USERNAME=
DB_PASSWORD=
DB_DRIVER=
DB_DDL=
DB_TYPE=
```

*.env.test file*

```.env
DB_URL=
DB_USERNAME=
DB_PASSWORD=
DB_DRIVER=
DB_TYPE=
```
### Run Application

#### Without Containerize the Application

```bash
# If maven install in your local machine
# Clean the project
mvn clean

# Build the project
mvn package

# Run the application
mvn spring-boot:run
```

```bash
# If not maven install in your local machine
# Clean the project
./mvnw clean

# Build the project
./mvnw package

# Run the application
./mvnw spring-boot:run
```

#### With Containerize the Application

### Run Test

```bash
# The project will used the application-test.yml file
./mvnw test -D --spring.profiles.active=test
```

### Directory Tree

```bash
.
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── monolith
    │   │           └── example
    │   │               ├── controller
    │   │               │   ├── AuthController.java
    │   │               │   ├── CustomerController.java
    │   │               │   ├── HomeController.java
    │   │               │   └── ProductController.java
    │   │               ├── dto
    │   │               │   ├── CustomerDto.java
    │   │               │   ├── CustomerResponseDto.java
    │   │               │   ├── LoginDto.java
    │   │               │   ├── ProductDto.java
    │   │               │   ├── ProductResponseDto.java
    │   │               │   └── SignUpDto.java
    │   │               ├── ExampleApplication.java
    │   │               ├── mapper
    │   │               │   ├── CustomerMapper.java
    │   │               │   └── ProductMapper.java
    │   │               ├── model
    │   │               │   ├── Customer.java
    │   │               │   └── Product.java
    │   │               ├── repository
    │   │               │   ├── CustomerRepository.java
    │   │               │   └── ProductRepository.java
    │   │               ├── response
    │   │               │   └── ApiResponse.java
    │   │               ├── runner
    │   │               │   └── RunnerComponent.java
    │   │               └── services
    │   │                   ├── AuthService.java
    │   │                   ├── CustomerService.java
    │   │                   ├── impl
    │   │                   │   ├── AuthServiceImpl.java
    │   │                   │   ├── CustomerServiceImpl.java
    │   │                   │   └── ProductServiceImpl.java
    │   │                   └── ProductService.java
    │   └── resources
    │       ├── application-test.yaml
    │       ├── application.yaml
    │       ├── static
    │       └── templates
    └── test
        ├── java
        │   └── com
        │       └── monolith
        │           └── example
        │               ├── controller
        │               │   ├── CustomerControllerTest.java
        │               │   ├── HomeControllerTest.java
        │               │   └── ProductControllerTest.java
        │               ├── e2e
        │               │   └── ConnectionE2ETest.java
        │               ├── ExampleApplicationTests.java
        │               ├── mapper
        │               │   ├── CustomerMapperTest.java
        │               │   └── ProductMapperTest.java
        │               └── service
        │                   ├── AuthServiceTest.java
        │                   ├── CustomerServiceTest.java
        │                   └── ProductServiceTest.java
        └── resources
            └── query.sql
```