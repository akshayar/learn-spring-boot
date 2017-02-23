# Objectives
1. A simple Spring Boot project with a single rest service. 
2. Explore Spring Boot Actuator support. 
3. Explore Spring properties support with profiles
  	* Multiple profiles. 
 	* Pass active profiles from command line both maven and java command. 
 	* Pass active profile using system environment varriable. 
4. Integration Testing Support. 
	* Create integration test which would only run in particular integration testing environment or when a particular profile is active. For example I am creating a service, I want to run a set of integration tests when I deploy on docker and another when I deploy on local host.
	* On pre-integration testing start the server using passed in profile. 
5. Explore Docker support
	* Create docker image using build and package. 
	* Push the image to docker hub. 
	* Complete Dev ops cycle. 
6. Explore cloud support
	* AWS deployment. 
	* Other cloud.
	* Cloud Foundary.  
	
		
# Tags
## 1.0
Simple Rest Service with 
* Maven fat jar support 
	* mvn package
	* java -jar target\learn-spring-boot-0.0.1-SNAPSHOT.jar	
* Maven run support
	* mvn spring-boot:run 
* Unit Test

