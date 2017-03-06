# Cloud Deployment
[Source Code](https://github.com/akshayar/learn-spring-boot)

[Project Home Page](https://github.com/akshayar/learn-spring-boot)

## The Example

1. Deploy on Heroku and CloudFoundry

## Heroku

1. Refer an excellent step by step guide for deploying Java application at [Heroku-Getting Started with Java ](https://devcenter.heroku.com/articles/getting-started-with-java#introduction)
2. There a few modifications. 
  1. Checkout this source code. 
  2. ~~From the root directory instead of deployment using `git push heroku master` use `git subtree push --prefix learn-spring-boot heroku master` . This had to be done since the pom.xml is not in the root directory but in a sub-direcotry by name learn-spring-boot.~~ 
3.  Code checkout
```shell
git clone https://github.com/akshayar/learn-spring-boot.git
## Tag 7.0-Heroku-Push and subsequent code has relevant code
git checkout 7.0-Heroku-Push
```
4. Here are the sequence of commands to run -
```
heroku create
git push heroku master
## The command below is used if pom is in a sub-folder
##git subtree push --prefix learn-spring-boot heroku master
heroku ps:scale web=1
heroku logs 
heroku open security/ping
## Execute the command below to stop the application.
#heroku ps:scale web=0
```

## Heroku Maven Plugin

1. Refer to Heroku guide of deploying application to Heroku using Maven Plugin [Heroku Deploying with Maven Plugin](https://devcenter.heroku.com/articles/deploying-java-applications-with-the-heroku-maven-plugin)
2. Complete plugin guide at [Heroku Maven Pluhin](https://github.com/heroku/heroku-maven-plugin).
3. Code checkout
```shell
git clone https://github.com/akshayar/learn-spring-boot.git
## Tag 8.0-Heroku-Maven-Plugin and subsequent code has relevant code
git checkout 8.0-Heroku-Maven-Plugin
``` 
4. Here are the sequence of command to run -
```
heroku create
mvn heroku:deploy -Pheroku
heroku open security/ping
heroku logs --tail
```

### Plugin Configuration
Following profile configuration is added , which ensure deployment on calling `mvn heroku:deploy -Pheroku`
```
        <profile>
			<id>heroku</id>
			<build>
				<plugins>
					<plugin>
						<groupId>com.heroku.sdk</groupId>
						<artifactId>heroku-maven-plugin</artifactId>
						<version>1.1.3</version>
						<configuration>
							<appName>${heroku.appName}</appName>
							<processTypes>
								<web>java $JAVA_OPTS -Dserver.port=$PORT -jar target/${project.name}-${project.version}.jar</web>
							</processTypes>
						</configuration>
					</plugin>
				</plugins>
			</build>
		</profile>
``` 

### Integration Testing with Heroku deployment

1. How to run integration test integrated with Heroku deployment
```
heroku apps
## pass applicaiton name as heroku.appName property
mvn verify -Pheroku -Dheroku.appName=limitless-sierra-85988
```

2. Maven configuration 
```xml
		<profile>
			<id>heroku</id>
			<build>
				<plugins>
					<plugin>
						<groupId>org.apache.maven.plugins</groupId>
						<artifactId>maven-failsafe-plugin</artifactId>
						<configuration>
							<systemPropertyVariables>
								<integration.env>heroku</integration.env>
							</systemPropertyVariables>
						</configuration>
					</plugin>
					<plugin>
						<groupId>com.heroku.sdk</groupId>
						<artifactId>heroku-maven-plugin</artifactId>
						<version>1.1.3</version>
						<configuration>
							<appName>${heroku.appName}</appName>
							<processTypes>
								<web>java $JAVA_OPTS -Dserver.port=$PORT -jar target/${project.name}-${project.version}.jar</web>
							</processTypes>
							<configVars>
								<SPRING_PROFILES_ACTIVE>heroku</SPRING_PROFILES_ACTIVE>
							</configVars>
						</configuration>
						<executions>
							<execution>
								<id>pre-integration</id>
								<phase>pre-integration-test</phase>
								<goals>
									<goal>deploy</goal>
								</goals>
							</execution>
						</executions>
					</plugin>
				</plugins>
			</build>
		</profile>

```

3. Integration Test class
```java
@RunWith(SpringRunner.class)
@IfProfileValue(name="integration.env",value="heroku")
public class SecurityControllerHerokuIT extends BaseIT{
	private RestTemplate restTemplate=new RestTemplate();
	private String serverUrl;

	@Before
	public void setup(){
		serverUrl=getServerUrl();
		System.out.println("In unit test server url:"+serverUrl);
	}

	@Test
	public void pingtest() {
		ResponseEntity<String> pingResponse=restTemplate.getForEntity(serverUrl+"/security/ping", String.class);
		assertEquals(HttpStatus.OK,pingResponse.getStatusCode());
		assertEquals("OK HEROKU",pingResponse.getBody());
	}
}
```
```java
public class BaseIT {
	
	protected String getServerUrl(){
		if(System.getProperty("docker.host.address")!=null){
			return "http://"+System.getProperty("docker.host.address")+":"+System.getProperty("server.port");
		}else if (System.getProperty("heroku.appName")!=null){
			return "https://"+System.getProperty("heroku.appName")+".herokuapp.com";
		}else{
			return "http://localhost:8080";
		}
	}
	

}
```
