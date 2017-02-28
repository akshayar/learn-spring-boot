# Docker Support 
[Source Code](../../learn-spring-boot)

[Project Home Page](https://github.com/akshayar/learn-spring-boot)

## Docker Pre-requisite

1. If you are on Ubuntu/Linus install Docker [Docker Installation](https://docs.docker.com/engine/installation/linux/).
2. If you are on Windows install [Docker Toolbox](https://www.docker.com/products/docker-toolbox) .


## The Example

1. Integrate with Maven plugin to 
  1. Build docker image during the build cycle
  2. Integration test with docker image created above
  3. Push created docker image to Docker Hub
2. The configuration should retain support for installation on local VM too. While building there should be choice to either build and test on docker or on local VM

## How to setup?

1. Get the source code
```shell
git clone https://github.com/akshayar/learn-spring-boot.git
```
If on Windows -
```shell
cd learn-spring-boot
### Windows
git checkout 4.0-Docker-Machine
```
If on Linux -
```shell
### Linux 
git checkout 5.0-Docker-Localhost
```
If on Windows, create docker-machine
```cmd
docker-machine create --driver virtualbox akshaya1
docker-machine ls
```
## How to run?
### Build and integration test
Go to *learn-spring-boot* directory and run with Maven. 
* Integration tests on local machine. Deploys the code on local machine. 
```
mvn verify -Pvm
```
* Integration tests on docker. Deploys the code on docker. 4.0-Docker-Machine code runs deploys the code on docker engine running on the configured docker machine. 5.0-Docker-Localhost deploys the code on docker engine running on local host.
```
mvn verify -Pdocker
```
### Build Docker Image
```
mvn clean package -Pdocker

```
### Push Docker Image to docker hub

Update the image name to point to a your own repository. Currently it points to arawa3/first-spring-boot/ which is my repository where I have permission to push. Edit <image>/<name> 

```
mvn docker:push -Pdocker -Ddocker.username=<username> -Ddocker.password=<password> -Ddocker.registry=registry.hub.docker.com
```

## Explaination

- Used the profiles setup in step 3 . Docker profile will be used to for docker and VM profile for local VM. 
- Following docker plugin configuration is added
```xml
					<plugin>
						<groupId>io.fabric8</groupId>
						<artifactId>docker-maven-plugin</artifactId>
						<version>0.19.0</version>
						<configuration>
							<!-- <machine>
								<name>akshaya1</name>
							</machine> -->
							<images>
								<image>
									<name>arawa3/first-spring-boot</name>
									<build>
										<from>java:8</from>
										<assembly>
											<descriptorRef>artifact</descriptorRef>
										</assembly>
										<ports>
											<port>8080</port>
										</ports>
										<cmd>java -jar maven/${project.name}-${project.version}.jar</cmd>
									</build>
									<run>
										<env>
											<SPRING_PROFILES_ACTIVE>docker</SPRING_PROFILES_ACTIVE>
										</env>
										<ports>
											<port>8080:8080</port>
										</ports>
										<wait>
											<time>15000</time>
											<url>http://${docker.host.address}:8080/security/ping</url>
										</wait>
									</run>
								</image>
							</images>
						</configuration>
						<executions>
							<execution>
								<id>docker:build</id>
								<phase>package</phase>
								<goals>
									<goal>build</goal>
								</goals>
							</execution>
							<execution>
								<id>docker:start</id>
								<phase>install</phase>
								<goals>
									<goal>run</goal>
								</goals>
							</execution>
							<execution>
								<id>docker:stop</id>
								<phase>install</phase>
								<goals>
									<goal>stop</goal>
								</goals>
							</execution>
							<execution>
								<id>start-it</id>
								<phase>pre-integration-test</phase>
								<goals>
									<goal>build</goal>
									<goal>start</goal>
								</goals>
							</execution>
							<execution>
								<id>stop-it</id>
								<phase>post-integration-test</phase>
								<goals>
									<goal>stop</goal>
								</goals>
							</execution>
						</executions>
					</plugin>

```
- The image buid configuraiton translates into following Dockerfile
```
FROM java:8
EXPOSE 8080
COPY maven /maven/
CMD java -jar maven/learn-spring-boot-0.0.1-SNAPSHOT.jar

```
-  The run configuration is.  
```xml
<run>
										<env>
											<SPRING_PROFILES_ACTIVE>docker</SPRING_PROFILES_ACTIVE>
										</env>
										<ports>
											<port>8080:8080</port>
										</ports>
										<wait>
											<time>15000</time>
											<url>http://${docker.host.address}:8080/security/ping</url>
										</wait>
									</run>
```
*Notice  SPRING_PROFILES_ACTIVE* environment property. This is to ensure that while starting property from docker profile is picked and used.

- If you are on Windows, the example needs to run with Docker Machine. For that un-comment  <machine> configuration and chose any for the docker machine. Refer [Docker Machine](https://docs.docker.com/machine/overview/) . 

- If you are on Ubuntu/Linux, comment or remove <machine> configuration. 


## Docker Learning Resources

### Pluralsight has some great resources to learn Docker

* Learning Path "Container Management using Docker" https://app.pluralsight.com/paths/skills/docker

* "Play by Play: Docker for Java Developers with Arun Gupta and Michael Hoffman" https://app.pluralsight.com/library/courses/play-by-play-docker-java-developers-arun-gupta-michael-hoffman/table-of-contents

* "Containers and Images: The Big Picture" https://app.pluralsight.com/library/courses/containers-images-big-picture/table-of-contents

### Docker Maven Plugin

https://dmp.fabric8.io/

### Docker Machine

https://docs.docker.com/machine/get-started/
