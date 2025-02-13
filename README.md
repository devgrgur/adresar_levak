
# Adresar Levak - Zadatak

- Web application created using Spring Boot 3 and Java 21
- CRUD operations
- REST API implementation
- Swagger (OpenAI) documentation
- Postgres database hosted on https://render.com/

## Prerequisites

- #### Java SE Development Kit 21
- #### Gradle
    
## Local Setup Instructions

Clone the repository & enter the cloned directory.

```bash
  git clone https://github.com/devgrgur/adresar_levak.git
  cd adresar_levak
```

Now you should be able to open the project in an IDE of your choice.

Let's setup our environment variables. In the root folder create a file called `env.properties` and place the variables inside. The connection details to the database will be included in my email.

```bash
  DATABASE_URL=
  DATABASE_USERNAME=
  DATABASE_PASSWORD=
```

Now we have to actually compile the code, package the application and download all dependencies (This will also run the tests).

```bash
  ./gradlew clean build --refresh-dependencies
```


To launch the actual application we will use.

```bash
  ./gradlew bootRun
```

The application should now be running! You should be able to access it here.

```bash
  http://localhost:8080/
```

## Swagger (OpenAI) 

All of the Swagger API documentation is visible here.

```bash
  http://localhost:8080/swagger
```

## Running tests locally

You can run tests locally using the command below.

```bash
  ./gradlew test
```
