#!/bin/bash
. ./setenv.sh
mkdir -p ${PROJECT_ROOT}/target/docker
cp docker/Dockerfile ${PROJECT_ROOT}/target/docker
cp ${PROJECT_ROOT}/target/${JAR_FILE_NAME} ${PROJECT_ROOT}/target/docker/${JAR_FILE_NAME}
cd ${PROJECT_ROOT}/target/docker
pwd
ls 
echo "Image name is ${ECR_IMAGE_NAME}"
docker image build -t ${ECR_IMAGE_NAME} .
docker image ls



