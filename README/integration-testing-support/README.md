## Integration Testing Support

### The example

1. The example does not do the setup for docker. That will follow in later tags. At present we assume that docker profile will have additional configurations to create docker images and deploy. 

### How to setup?

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
4.Add pre-integration and post-integration phases in spring-boot plugin. For docker configuration that needs to be done in corresponding configuration. 
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
5.Add profile configuration. Active profile is passed as JVM argument. We are passing it here and not from command line , since passing from command line affects running on regular UT which should pick the properties from UT's source of properties file. 
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
6.Add integration tests with IT suffix.  Notice @IfProfileValue annotation. This is required since for **docker** profile we don't want these set of tests to run. To enable this from command line a JVM argument needs to be passed *-Dintegration.env=vm*. 

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
### How to run 
* Integration tests on VM
```
mvn verify -Pvm
```
* Integration tests on docker
```
mvn verify -Pdocker
```
