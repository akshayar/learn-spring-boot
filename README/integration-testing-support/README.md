## Integration Testing Support

[Source Code](../../learn-spring-boot)

[Project Home Page](https://github.com/akshayar/learn-spring-boot)

### The example

1. The example does not do the setup for docker. That will follow in later tags. At present we assume that docker profile will have additional configurations to create docker images and deploy. 

### How to setup?

1. Checkout the source code from tag 3.0.
```
git clone https://github.com/akshayar/learn-spring-boot.git
cd learn-spring-boot
git checkout 3.0
```
### How to run?
Go to *learn-spring-boot* directory and run with Maven. 
* Integration tests on VM
```
mvn verify -Pvm
```
* Integration tests on docker
```
mvn verify -Pdocker
```
### Explaination
1. 2 profiles are setup. One for VM and another for docker. The docker plugin is not configured in this tag. The example just demonstrates the ability to run integration test in 2 different environments.
2. 2 sets of properties are created application-docker.properties and application-vm.properties. 	
3. *maven-failsafe-plugin* plugin is configured for integration test
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
4.  pre-integration and post-integration phases are configured for spring-boot plugin since its the spring-boot plugin which would do the job of starting (pre integration) and stopping (post integration) the SpringBoot service. 
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
5.Add profile configuration for VM and docker profiles. Active profile is passed as JVM argument. We are passing it here and not from command line , since passing from command line affects running on regular UT which should pick the properties from UT's source of properties file. Both of these profiles are running SpringBoot applicaiton in local machine/vm. It is just to illustrate that integration tests could be run in 2 different environments using 2 different profiles (as is done in later tags where docker profile runs tests on application deployed and running on docker). 
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
6. Integration tests are created with IT suffix.  Notice @IfProfileValue annotation. This is required since for **docker** profile we don't want these set of tests to run. To enable this from command line a JVM argument needs to be passed *-Dintegration.env=vm*. 

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
