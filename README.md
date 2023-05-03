## Testing Strategy

In order to ensure the stability and correctness of the application, a combination of the following testing strategies will be used:

**1. Unit Tests**

   Unit tests will be used to test each individual component of the microservices. They will be written in isolation and should <ins>cover all</ins> of the application's business logic.
   Tools: JUnit, Mockito

**2. Integration Tests**

   Integration tests will be used to test the interactions between different components of the microservices. Test databases (such as H2) will be used and the repository layer of the microservices will be covered.
   Tools: JUnit

**3. Contract Tests**

   Contract tests will be used to test the contracts between different microservices. The Spring Cloud Contract framework will be used and the tests will be stored in the microservice that defines the contract.
   Tools: Spring Cloud Contract, JUnit, Mockito

**4. Component Tests**

   Component tests will be used to test the end-to-end flow of each microservice (as far as a acts microservice acts as a component for our microservice architecture). They will be written in a separate project using the Cucumber framework. The component tests will cover the entire flow of each microservice of particual functionality.
   Tools: Cucumber, RestAssured, JUnit

**5. End-to-End Tests**

   End-to-end tests will be used to test the entire flow of the application. They will be written in a separate project using the Cucumber framework(will be stored together with component tests).
   Tools: Cucumber, RestAssured, JUnit

By using this combination of testing strategies, the stability and correctness of the application will be ensured. Unit tests will ensure that each component is working correctly, integration tests will ensure that the components are working together correctly, contract tests will ensure that the contracts, component tests will ensure that the microservices are working correctly, and end-to-end tests will ensure that the entire flow of the application is working correctly.