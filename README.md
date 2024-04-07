# TextAnalyZer
Web-service created for analyZing text and extracting info about automobiles

## Technologies and dependencies
+ Java
+ Maven, Spring boot
+ PostgreSQL
---
## The condition of the laboratory
1. Realised all CRUD operations on all entities. Create and run locally the simplest web/REST service using any open source example using Java stack: Spring (Spring Boot)/maven/gradle/Jersey/ Spring MVC.  Add a GET endpoint that accepts input parameters as query Params in the URL according to the option, and returns any hard-coded result in the form of JSON according to the option.
2. Connect a database to the project (PostgreSQL/MySQL/etc.). Implementation of one-to-many communication @OneToMany. The implementation of the many-to-many connection @ManyToMany. Implement CRUD operations with all entities.
3. Add an endpoint to the GET project (it should be useful) with the parameter(s). The data must be obtained from the database using a "custom" query (@Query) with parameter(s) to the nested entity. Add the simplest cache in the form of an in-memory Map (as a separate bin).
4. Handle 400 and 500 errors. Add logging of actions and errors (aspects). Connect Swagger & CheckStyle. To remove stylistic errors.
## SonarCloud
[Quality](https://sonarcloud.io/summary/overall?id=carbon2204_JavaProj)
