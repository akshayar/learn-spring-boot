**Table of Contents**  *generated with [DocToc](http://doctoc.herokuapp.com/)*

- [Objectives](#)
- [Step 1 - Tag 1.0](#)
- [Step 2 - Tag 2.0](#)
- [Step 3 - Tag 3.0](#)
# Objectives

The objective of this project is to create a simple Spring Boot project to start with and then progressively add layers of complexities which can be applied to real world projects. 

1. A simple Spring Boot project with a single rest service. 
2. Explore Spring properties support with profiles
  	* Multiple profiles. 
 	* Pass active profiles from command line both maven and java command. 
 	* Pass active profile using system environment varriable. 
3. Integration Testing Support. 
	* Create integration test which would only run in particular integration testing environment or when a particular profile is active. For example I am creating a service, I want to run a set of integration tests when I deploy on docker and another when I deploy on local host.
	* On pre-integration testing start the server using passed in profile. 
4. Explore Docker support
	* Create docker image using build and package. 
	* Push the image to docker hub. 
	* Complete Dev ops cycle. 
5. Explore cloud support
	* AWS deployment. 
	* Other cloud.
	* Cloud Foundary.  
6. Explore Spring Boot Actuator support. 

# Step 1	- Tag 1.0
Simple Rest Service with 
* Maven fat jar support 
	* mvn package
	* java -jar target\learn-spring-boot-0.0.1-SNAPSHOT.jar	
* Maven run support
	* mvn spring-boot:run 
* Unit Test

# Step 2 - Tag 2.0 
Property Support - profiles
* Use property for a profile - pass JMV argument
	* java -jar -Dspring.profiles.active=vm target\learn-spring-boot-0.0.1-SNAPSHOT.jar	
* Use property for a profile - pass env varriable
	* set SPRING_PROFILES_ACTIVE=vm
	* java -jar target\learn-spring-boot-0.0.1-SNAPSHOT.jar	

# Step 3 - Tag 3.0 
Integration Testing Support

1. The idea is to test and deploy application on either VM or on docker. 
2. We should be able to do run integration test on either of them
3. During pre-integration-test deploy the application on docker on local VM/machine.
4. Run the integration test. 
5. Stop application after post integration test. 
