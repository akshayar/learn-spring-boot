# DevOps Cycle 
[Source Code](https://github.com/akshayar/learn-spring-boot)

[Project Home Page](https://github.com/akshayar/learn-spring-boot)

## The Example

1. Integrate with [Circle CI](https://circleci.com/) CI portal. 
2. Create periodic build which triggers on each commit, runs integration tests on Docker and push the created Docker image on Docker hub or deploys to Heroku. 

## Circle CI + GitHub Setup

1. Refer [Getting Started with Circle CI] (https://circleci.com/docs/1.0/getting-started/). 
2. Create a Circle CI account and [add GitHub project](https://circleci.com/add-projects).
3. If the project follows directory structure i.e. the pom.xml is in root directory, this should be good enough. 
4. If not or for advanced configuration add  circle.yml file. 
5. By default the build triggers on each push to GitHub. The current project configuration does trigger on each project commit. 

## Git Hub + Circle CI + Docker + Docker Hub Push

### Setup
1. Get the source code
```shell
git clone https://github.com/akshayar/learn-spring-boot.git
## Tag 6.0-Docker-CircleCI-PUSH-HUB and subsequent  code has relevant code.
git checkout 6.0-Docker-CircleCI-PUSH-HUB
```
2. Ensure that in pom.xml Docker machine configuration (<machine>) is commented


### Explanation

1. The example build on Docker image creation in previous steps. Add following circle.yml configuration. The YAML parser is fragile to extra spaces. Please ensure the configuration is right else the build fails during configuration. 
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
4. Following test configuration is added for test report from maven run test cases. This is a standard configuration which needs to be copied. 
```yaml
test:
  post:
    - mkdir -p $CIRCLE_TEST_REPORTS/junit/
    - find . -type f -regex ".*/target/surefire-reports/.*xml" -exec cp {} $CIRCLE_TEST_REPORTS/junit/ \;
  override:
  - mvn verify -Pdocker -Ddocker.keepRunning 
```
5. The test run command is overwritten to run `mvn verify -Pdocker -Ddocker.keepRunning ` command to run tests. Docker profile is specified to build docker image and run integration test on docker.  `-Ddocker.keepRunning` is used to ensure that Docker stop goal is not attempted as post-integration-test step. There is bug which fails to stop Docker container after integration test run. 
6. Following configuration is added to push the resulting docker image to GitHub. DOCKER_USER, DOCKER_PASS and DOCKER_EMAIL are environment variables to be configured in CircleCI portal.  
```yaml
deployment:
  hub:
    branch: master
    commands:
      - docker login -u $DOCKER_USER -p $DOCKER_PASS -e $DOCKER_EMAIL
      - docker push arawa3/first-spring-boot

```

## Git Hub + Circle CI + Heroku + Heroku Deploy

### Setup
1. Get the source code
```shell
git clone https://github.com/akshayar/learn-spring-boot.git
## Tag 9.0-Heroku-CircleCI-Deploy and subsequent  code has relevant code.
git checkout 6.0-Docker-CircleCI-PUSH-HUB
```

### Explanation

1. The example build on Heroku deployment example at [Heroku Deployment](../cloud-deployment) . Add circle.yml configuration. The YAML parser is fragile to extra spaces. Please ensure the configuration is right else the build fails during configuration. 
```yaml
test:
  post:
    - mkdir -p $CIRCLE_TEST_REPORTS/junit/
    - find . -type f -regex ".*/target/surefire-reports/.*xml" -exec cp {} $CIRCLE_TEST_REPORTS/junit/ \;
  override:
  - mvn verify -Pheroku -Dheroku.appName=$HEROKU_APP_NAME 

deployment:
  test:
    branch: master
    commands:
      - mvn heroku:deploy -Pheroku -DskipTests -Dheroku.appName=$HEROKU_APP_NAME
      - heroku ps:scale web=1 --app $HEROKU_APP_NAME
```
2. Add HEROKU_API_KEY	and HEROKU_APP_NAME environment variables under "BUILD SETTINGS ->  Environment Variables". 
3. Following test configuration is added for test report from maven run test cases. This is a standard configuration which needs to be copied. 
```yaml
test:
  post:
    - mkdir -p $CIRCLE_TEST_REPORTS/junit/
    - find . -type f -regex ".*/target/surefire-reports/.*xml" -exec cp {} $CIRCLE_TEST_REPORTS/junit/ \;
  override:
  - mvn verify -Pheroku -Dheroku.appName=$HEROKU_APP_NAME 
```
5. The test run command is overwritten to run `mvn verify -Pheroku -Dheroku.appName=$HEROKU_APP_NAME ` command to run tests. 
6. Following configuration is added to deploy to Heroku.  
```yaml
deployment:
  test:
    branch: master
    commands:
      - mvn heroku:deploy -Pheroku -DskipTests -Dheroku.appName=$HEROKU_APP_NAME
      - heroku ps:scale web=1 --app $HEROKU_APP_NAME
```
## Issues and resolution
1. "Build Failure - Unable to remove container" . This is because at CircleCI runtime the docker container can not be stopped/removed as part of post-integration step. This need not be done. 
2. "NO TESTS". For this add test.post configuration as mentioned above. 
3. "Action failed: Configure the build". This is due to error in parsing circle.yml. Use online YAML editors to identify issues. 
4. "Cannot create docker access object: Failed to start 'docker-machine status akshaya1' : Cannot run program "docker-machine": error=2, No such file or directory" . Remove docker machine configuration which is only required to run on windows machine. 
5. "There was an error while parsing your circle.yml: deployment section :hub must contain a branch or a tag". Branch configuration in deployment section was missing. 
6. "DOCKER> Unable to pull 'java:8'" . This was a temporary error which went after a while. 
7. "Failed to deploy application: Could not get API key! Please install the toolbelt and login with `heroku login` or set the HEROKU_API_KEY environment variable." Add HEROKU_API_KEY as CircleCI environment variable. 
8. "There was an error while parsing your circle.yml: deployment section :test should contain only one of heroku:, commands:, or codedeploy:" .  The error came when deployment.test.commands and deployment.test.heroku were setup. Removed deployment.test.heroku. 
9. The application was deployed by the URL was not working. This was so, since not dynos were running for app. The command `heroku ps` revealed "No dynos on limitless-sierra-85988" .  Added `heroku ps:scale web=1 --app $HEROKU_APP_NAME`
10. Following error was came with `heroku ps:scale web=1`. Added `--appp $HEROKU_APP_NAME`
```
heroku ps:scale web=1
 ▸    Error: No app specified
 ▸    Usage: heroku ps:scale --app APP
 ▸    We don't know which app to run this on.
 ▸    Run this command from inside an app folder or specify which app to use with --app APP
 ▸    
 ▸    https://devcenter.heroku.com/articles/using-the-cli#app-commands

heroku ps:scale web=1 returned exit code 2

Action failed: heroku ps:scale web=1
```

## Learning Resources
1. Pluralsight course https://www.pluralsight.com/courses/integrating-docker-with-devops-automated-workflows gives a high level introduction. 
