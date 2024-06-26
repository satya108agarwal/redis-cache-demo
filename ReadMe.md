# Read Me First
Sample Spring Boot Application with Redis Database and Cache
This is a sample Spring Boot application demonstrating how to integrate Redis as both a database and a caching solution. The application is built with Java 17 and managed with Gradle.

## Prerequisites
Before you begin, ensure you have the following installed:

- Java Development Kit (JDK) 17
- Docker (for running Redis locally)
- Gradle (dependency management and build tool)

## Setting Up Redis Locally with Docker
To run Redis locally using Docker, execute the following command:

```bash

docker run --name my-redis-container -d -p 6379:6379 redis
 ```

# Getting Started

## Running the Spring Boot Application in local
Clone the repository and navigate into the project directory.

Open a terminal and run the Spring Boot application using Gradle:

```bash
./gradlew bootRun
```
This command compiles the application, runs tests, and starts the Spring Boot server.

Access the application at http://localhost:8080.

## Running it in Tanzu Platform for Cloud foundry

### Dependencies
Ensure your Spring Boot application includes the necessary dependencies for Cloud Foundry integration. One important dependency is java-cfenv-boot, which allows your application to easily access Cloud Foundry environment variables and services.

```xml
<dependency>
    <groupId>io.pivotal.cfenv</groupId>
    <artifactId>java-cfenv-boot</artifactId>
    <version>3.1.5</version>
</dependency>

```
**Note**: This dependency (java-cfenv-boot) is crucial for retrieving service bindings and configuration from Cloud Foundry's environment.

### Bind Redis service
```bash
cf marketplace -e p.redis //command to list all the plans. 

cf create-service p-redis shared-vm demo-redis
```

### Manifest File
Create a manifest.yml file in the root directory of your Spring Boot application. This file defines deployment settings such as application name, memory allocation, instances, and service bindings.

```yaml
applications:
  - name: redis-cache-demo
    instances: 1
    memory: 1G
    disk_quota: 1G
    buildpacks:
      - java_buildpack_offline
    services:
      - demo-redis
    path: build/libs/redis-cache-demo-0.0.1-SNAPSHOT.jar
    env:
      JBP_CONFIG_OPEN_JDK_JRE: '{ jre: { version: 17.+ } }'
      SPRING_PROFILES_ACTIVE: cloud
 ```
### Explanation:
**name:**  Specifies the name of your application on Cloud Foundry.
**memory:** Sets the allocated memory for your application instance.
**instances:** Defines the number of instances to run.
**path:** Points to the location of your built JAR file.
**buildpacks:** Specifies the buildpack to use (java_buildpack for Java applications).
**services:** Lists the names of services (like p-redis) that your application depends on. Ensure these services are already created and bound to your application.
**env:** Sets environment variables specific to your application (SPRING_PROFILES_ACTIVE in this case).

### Deploying
Use the cf push command to deploy your application to Cloud Foundry. Ensure you have the Cloud Foundry CLI installed and configured to connect to your Cloud Foundry environment.

```bash
cf push
```
This command reads the manifest.yml file in the current directory and deploys your application according to the specified settings.

### Summary
Deploying a Spring Boot application to Cloud Foundry involves setting up dependencies like java-cfenv-boot, configuring a manifest.yml file with deployment specifics, and using the cf push command for deployment. Ensure your application is configured to utilize the cloud profile to adapt to the Cloud Foundry environment seamlessly.

# Notes

# Caching Strategies:
## Inline Synchronous vs Asynchronous
### Inline Synchronous Cache
Inline synchronous caching (also known as blocking cache) refers to caching where the method execution blocks until the cache operation is complete. This ensures that subsequent requests for the same data wait for the cache to be updated or retrieved.

### Pros:

- Simplifies code and logic by handling cache updates synchronously.
- Ensures strong consistency between data store and cache.
### Cons:
- Performance can be impacted when cache operations take longer.
- Can lead to higher latency for requests if cache updates are slow.

## Asynchronous Cache
Asynchronous caching involves updating or retrieving cache data without blocking the main execution thread. This is typically achieved using background tasks or threads to manage cache updates independently of the main application logic.

### Pros:

- Improves application responsiveness by allowing non-blocking cache operations.
- Reduces the impact on request latency as cache operations can proceed independently.
### Cons:

- Complexity increases as it requires managing asynchronous tasks and potential data consistency issues.
- May introduce additional overhead and resource consumption with thread management.

## When to Use Each Approach
**Inline Synchronous Cache:** Use when data consistency is critical and cache operations are expected to be fast and reliable. Suitable for scenarios where blocking the request thread momentarily is acceptable.

**Asynchronous Cache:** Prefer when improving application responsiveness and minimizing request latency is crucial. Ideal for scenarios with potentially slow cache operations or where maintaining high concurrency is important.

Choose the caching strategy based on your application's performance requirements, data consistency needs, and the characteristics of cache operations.

