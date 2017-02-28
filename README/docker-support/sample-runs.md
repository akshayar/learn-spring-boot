## Create docker-machine sample run
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
## Docker Integration Test Sample Output
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

## Build Docker Image Sample Run
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

## Docker Push Sample Run

```
[INFO] DOCKER> Docker machine "akshaya1" is running
[INFO] DOCKER> DOCKER_HOST from docker-machine "akshaya1" : tcp://192.168.99.104:2376

[INFO] DOCKER> The push refers to a repository [registry.hub.docker.com/arawa3/first-spring-boot]
#
[INFO] DOCKER> latest: digest: sha256:add4766558e3f487a00c6fb6fc633fd715382bbcde8acd58fc326be2d406ebc0 size: 2212

[INFO] DOCKER> Pushed arawa3/first-spring-boot in 7 seconds
```

