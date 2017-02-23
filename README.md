# Objectives
1. A simple Spring Boot project with a single rest service. 
2. Explore Spring Boot Actuator support. 
3. Explore Spring properties support with profiles
	3.a Multiple profiles. 
	3.b Pass active profiles from command line both maven and java command. 
	3.c Pass active profile using system environment varriable. 
4. Integration Testing Support. 
	4.a Create integration test which would only run in particular integration testing environment or when a particular profile is active. For example I am creating a service, I want to run a set of integration tests when I deploy on docker and another when I deploy on local host. 
	4.b On pre-integration testing start the server using passed in profile. 
5. Explore Docker support
	5.a Create docker image using build and package. 
	5.b Push the image to docker hub. 
	5.c Complete Dev ops cycle. 
6. Explore cloud support
	6.a AWS deployment. 
	6.b Other cloud.
	6.c Cloud Foundary.  
	
		
