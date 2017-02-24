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

## 2.0 
Property Support - profiles
* Use property for a profile - pass JMV argument
	* java -jar -Dspring.profiles.active=vm target\learn-spring-boot-0.0.1-SNAPSHOT.jar	
* Use property for a profile - pass env varriable
	* set SPRING_PROFILES_ACTIVE=vm
	* java -jar target\learn-spring-boot-0.0.1-SNAPSHOT.jar	

## 3.0 
Integration Testing Support
1. The idea is to test and deploy application on either VM or on docker. 
2. We should be able to do run integration test on either of them
3. During pre-integration-test deploy the application on docker on local VM/machine.
4. Run the integration test. 
5. Stop application after post integration test. 

The example
1. The example does not do the setup for docker. That will follow in later tags. At present we assume that docker profile will have additional configurations to create docker images and deploy. 

How to setup?
1. Setup 2 profiles. One for VM and another for docker. 
2. Create 2 sets of properties. 	
3. Setup plugin for integration test
```xml
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-failsafe-plugin</artifactId>
	<executions>
		<execution>
			<goals>
				<goal>integration-test</goal>
				<goal>verify</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```
4. Add pre-integration and post-integration phases in spring-boot plugin. For docker configuration that needs to be done in corresponding configuration. 
```xml
<plugin>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-maven-plugin</artifactId>
	<executions>
		<execution>
			<id>pre-integration</id>
			<phase>pre-integration-test</phase>
			<goals>
				<goal>repackage</goal>
				<goal>start</goal>
			</goals>
		</execution>
		<execution>
			<id>post-integration</id>
			<phase>post-integration-test</phase>
			<goals>
				<goal>stop</goal>
			</goals>
		</execution>
	</executions>
</plugin>

```
5. Add profile configuration. Active profile is passed as JVM argument. We are passing it here and not from command line , since passing from command line affects running on regular UT which should pick the properties from UT's source of properties file. 
```
<profiles>
		<profile>
			<id>vm</id>
			<build>
				<plugins>
					<plugin>
						<groupId>org.springframework.boot</groupId>
						<artifactId>spring-boot-maven-plugin</artifactId>
						<configuration>
							<jvmArguments>-Dspring.profiles.active=vm</jvmArguments>
						</configuration>
					</plugin>
				</plugins>
			</build>
		</profile>
		<profile>
			<id>docker</id>
			<build>
				<plugins>
					<plugin>
						<groupId>org.springframework.boot</groupId>
						<artifactId>spring-boot-maven-plugin</artifactId>
						<configuration>
							<jvmArguments>-Dspring.profiles.active=docker</jvmArguments>
						</configuration>
					</plugin>
				</plugins>
			</build>
		</profile>
	</profiles>
```
6. Add integration tests with IT suffix.  Notice @IfProfileValue annotation. This is required since for **docker** profile we don't want these set of tests to run. To enable this from command line a JVM argument needs to be passed *-Dintegration.env=vm*. 

```java
@RunWith(SpringRunner.class)
@IfProfileValue(name="integration.env",value="vm")
public class SecurityControllerVMIT {


	private RestTemplate restTemplate=new RestTemplate();
	private String serverUrl="http://localhost:8080";

	@Test
	public void pingtest() {
		ResponseEntity<String> 		   pingResponse=restTemplate.getForEntity(serverUrl+"/security/ping", String.class);
		assertEquals(HttpStatus.OK,pingResponse.getStatusCode());
		assertEquals("OK VM",pingResponse.getBody());
	}
}
```
6. Sample run. 
* Integration tests on VM
```
mvn verify -Dintegration.env=vm -Pvm
```
* Integration tests on docker
```
mvn verify -Dintegration.env=docker -Pdocker
```
