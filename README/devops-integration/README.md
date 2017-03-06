# DevOps Cycle 
[Source Code](https://github.com/akshayar/learn-spring-boot)

[Project Home Page](https://github.com/akshayar/learn-spring-boot)

## The Example

1. Integrate with [Circle CI](https://circleci.com/) CI portal. 
2. Create periodic build which triggers on each commit, runs integration tests on Docker and push the created Docker image on Docker hub. 

## Circle CI Integration

1. Refer [Getting Started with Circle CI] (https://circleci.com/docs/1.0/getting-started/). 
2. Create a Circle CI account and [add GitHub project](https://circleci.com/add-projects).
3. If the project follows directory structure i.e. the pom.xml is in root directory, this should be good enough. 
4. If not or for advanced configuration add  circle.yml file. 
5. By default the build triggers on each push to GitHub. The current project configuration does trigger on each project commit. 
### Setup
1. Get the source code
```shell
git clone https://github.com/akshayar/learn-spring-boot.git
## Tag 6.0-Docker-CircleCI-PUSH-HUB and subsequent  code has relevant code.
git checkout 6.0-Docker-CircleCI-PUSH-HUB
```


### Explanation

1. Circle.yml configuration. The YAML parser is fragile to extra spaces. Please ensure the configuration is right else the build fails during configuration. 
```yaml
machine:
  services:
    - docker
test:
  post:
    - mkdir -p $CIRCLE_TEST_REPORTS/junit/
    - find . -type f -regex ".*/target/surefire-reports/.*xml" -exec cp {} $CIRCLE_TEST_REPORTS/junit/ \;
  override:
  - mvn verify -Pdocker -Ddocker.keepRunning 
 
deployment:
  hub:
    branch: master
    commands:
      - docker login -u $DOCKER_USER -p $DOCKER_PASS -e $DOCKER_EMAIL
      - docker push arawa3/first-spring-boot
```
2. If the pom.xml is not in the root of GitHub project, add following -
```yaml
general:
  build_dir: learn-spring-boot

```
3. Docker service is added to build and create docker image during integration test and a final deliverable to be pushed to Docker registry in this case it would be DockerHub. 
4. Following test configuration is added test report from maven run test cases. This is a standard configuration which needs to be copied. 
```yaml
test:
  post:
    - mkdir -p $CIRCLE_TEST_REPORTS/junit/
    - find . -type f -regex ".*/target/surefire-reports/.*xml" -exec cp {} $CIRCLE_TEST_REPORTS/junit/ \;
  override:
  - mvn verify -Pdocker -Ddocker.keepRunning 
```
5. The test run command is overwritten to run `mvn verify -Pdocker -Ddocker.keepRunning ` command to run tests. `-Ddocker.keepRunning` is used to ensure that Docker stop goal is not attempted as post-integration-test step. There is bug which fails to stop Docker container after integration test run. 
6. Following configuration is added to push the resulting docker image to GitHub. DOCKER_USER, DOCKER_PASS and DOCKER_EMAIL are environment variables to be configured in CircleCI portal.  
```yaml
deployment:
  hub:
    branch: master
    commands:
      - docker login -u $DOCKER_USER -p $DOCKER_PASS -e $DOCKER_EMAIL
      - docker push arawa3/first-spring-boot

```

## Learning Resources
1. Pluralsight course https://www.pluralsight.com/courses/integrating-docker-with-devops-automated-workflows gives a high level introduction. 
