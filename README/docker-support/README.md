# Docker Support 
[Source Code](../../learn-spring-boot)

[Project Home Page](https://github.com/akshayar/learn-spring-boot)

## Docker Pre-requisite

1. If you are on Ubuntu/Linus install Docker [Docker Installation](https://docs.docker.com/engine/installation/linux/).
2. If you are on Windows install [Docker Toolbox](https://www.docker.com/products/docker-toolbox) .


## Example

1. Integrate with Maven plugin to 
  1. Build docker image during the build cycle
  2. Integration test with docker image created above
  3. Integrate with CI tool which automates build and integration cycle. 
  4. Push created docker image to Docker Hub
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
D:\learn-spring-boot\learn-spring-boot>docker-machine create --driver virtualbox akshaya1
Running pre-create checks...
Creating machine...
(akshaya1) Copying C:\Users\<userid>\.docker\machine\cache\boot2docker.iso to C:\Users\<userid>\.docker\machine\machines\akshaya1\boot2docker.iso...
(akshaya1) Creating VirtualBox VM...
(akshaya1) Creating SSH key...
(akshaya1) Starting the VM...
(akshaya1) Check network to re-create if needed...
(akshaya1) Waiting for an IP...
Waiting for machine to be running, this may take a few minutes...
Detecting operating system of created instance...
Waiting for SSH to be available...
Detecting the provisioner...
Provisioning with boot2docker...
Copying certs to the local machine directory...
Copying certs to the remote machine...
Setting Docker configuration on the remote daemon...
Checking connection to Docker...
Docker is up and running!
To see how to connect your Docker Client to the Docker Engine running on this virtual machine, run: docker-machine env akshaya1

D:\learn-spring-boot\learn-spring-boot>docker-machine ls
NAME       ACTIVE   DRIVER       STATE     URL                         SWARM   DOCKER    ERRORS
akshaya1   -        virtualbox   Running   tcp://192.168.99.102:2376           v1.13.1
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
Sample Output
```
[INFO] <<< docker-maven-plugin:0.19.0:build (docker:build) < package @ learn-spring-boot <<<
[INFO] 
[INFO] --- docker-maven-plugin:0.19.0:build (docker:build) @ learn-spring-boot ---
[INFO] DOCKER> Docker machine "akshaya1" is running
[INFO] DOCKER> DOCKER_HOST from docker-machine "akshaya1" : tcp://192.168.99.104:2376

[INFO] DOCKER> Pulling from library/java
######
[INFO] DOCKER> Digest: sha256:c1ff613e8ba25833d2e1940da0940c3824f03f802c449f3d1815a66b7f8c0e9d

[INFO] DOCKER> Status: Downloaded newer image for java:8

[INFO] DOCKER> Pulled java:8 in 35 seconds 
[INFO] Copying files to D:\learn-spring-boot\learn-spring-boot\target\docker\<userid>\first-spring-boot\build\maven
[INFO] Building tar: D:\learn-spring-boot\learn-spring-boot\target\docker\<userid>\first-spring-boot\tmp\docker-build.tar
[INFO] DOCKER> docker-build.tar: Created [arawa3/first-spring-boot] in 2 seconds 
[INFO] DOCKER> [arawa3/first-spring-boot]: Built image sha256:7c935
[INFO] 
[INFO] --- docker-maven-plugin:0.19.0:start (start-it) @ learn-spring-boot ---
[INFO] DOCKER> Docker machine "akshaya1" is running
[INFO] DOCKER> DOCKER_HOST from docker-machine "akshaya1" : tcp://192.168.99.104:2376
[INFO] DOCKER> [arawa3/first-spring-boot]: Start container 6b527bf193a4
[INFO] DOCKER> [arawa3/first-spring-boot]: Waiting on url http://192.168.99.104:8080/security/ping.
[INFO] DOCKER> [arawa3/first-spring-boot]: Waited on url http://192.168.99.104:8080/security/ping 6261 ms
[INFO] 
[INFO] --- maven-failsafe-plugin:2.18.1:integration-test (default) @ learn-spring-boot ---
.
<test run>
.
[INFO] --- docker-maven-plugin:0.19.0:stop (stop-it) @ learn-spring-boot ---
[INFO] DOCKER> Docker machine "akshaya1" is running
[INFO] DOCKER> DOCKER_HOST from docker-machine "akshaya1" : tcp://192.168.99.104:2376
[INFO] DOCKER> [arawa3/first-spring-boot]: Stop and removed container 6b527bf193a4 after 0 ms
```

### Build Docker Image
```
mvn clean package -Pdocker

```
Sample Run
```
[INFO] <<< docker-maven-plugin:0.19.0:build (docker:build) < package @ learn-spring-boot <<<
[INFO]
[INFO] --- docker-maven-plugin:0.19.0:build (docker:build) @ learn-spring-boot ---
[INFO] DOCKER> Docker machine "akshaya1" is running
[INFO] DOCKER> DOCKER_HOST from docker-machine "akshaya1" : tcp://192.168.99.104:2376
[INFO] Copying files to D:\learn-spring-boot\learn-spring-boot\target\docker\arawa3\first-spring-boot\build\maven
[INFO] Building tar: D:\learn-spring-boot\learn-spring-boot\target\docker\arawa3\first-spring-boot\tmp\docker-build.tar
[INFO] DOCKER> docker-build.tar: Created [arawa3/first-spring-boot] in 3 seconds
[INFO] DOCKER> [arawa3/first-spring-boot]: Built image sha256:e039a
[INFO] DOCKER> [arawa3/first-spring-boot]: Removed old image sha256:7c935
```
### Push Docker Image to docker hub

1. Update the image name to point to a your own repository. Currently it points to arawa3/first-spring-boot/ which is my repository where I have permission to push. Edit <image>/<name> 

```
mvn docker:push -Pdocker -Ddocker.username=<username> -Ddocker.password=<password> -Ddocker.registry=registry.hub.docker.com
```
Sample Run

```
[INFO] DOCKER> Docker machine "akshaya1" is running
[INFO] DOCKER> DOCKER_HOST from docker-machine "akshaya1" : tcp://192.168.99.104:2376

[INFO] DOCKER> The push refers to a repository [registry.hub.docker.com/arawa3/first-spring-boot]
#
[INFO] DOCKER> latest: digest: sha256:add4766558e3f487a00c6fb6fc633fd715382bbcde8acd58fc326be2d406ebc0 size: 2212

[INFO] DOCKER> Pushed arawa3/first-spring-boot in 7 seconds
```

## Explaination

1. Used the profiles setup in step 3 . Docker profile will be used to for docker and VM profile for local VM. 
2. Following docker plugin configuration is added
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
3. The image buid configuraiton translates into following Dockerfile

```
FROM java:8
EXPOSE 8080
COPY maven /maven/
CMD java -jar maven/learn-spring-boot-0.0.1-SNAPSHOT.jar

```
4.  The run configuration is.  
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

5. If you are on Windows, the example needs to run with Docker Machine. For that un-comment  <machine> configuration and chose any for the docker machine. Refer [Docker Machine](https://docs.docker.com/machine/overview/) . 

6. If you are on Ubuntu/Linux, comment or remove <machine> configuration. 


## Docker Learning Resources

### Pluralsight has some great resources to learn Docker

* Learning Path "Container Management using Docker" https://app.pluralsight.com/paths/skills/docker

* "Play by Play: Docker for Java Developers with Arun Gupta and Michael Hoffman" https://app.pluralsight.com/library/courses/play-by-play-docker-java-developers-arun-gupta-michael-hoffman/table-of-contents

* "Containers and Images: The Big Picture" https://app.pluralsight.com/library/courses/containers-images-big-picture/table-of-contents

### Docker Maven Plugin

https://dmp.fabric8.io/

### Docker Machine

https://docs.docker.com/machine/get-started/
