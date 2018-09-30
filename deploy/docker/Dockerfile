from alpine
run apk update && apk add openjdk8-jre
copy . /app
workdir /app
cmd ["/usr/bin/java","-Dserver.port=80","-jar","learn-spring-boot-0.0.1-SNAPSHOT.jar"]



