#!/bin/bash
. ./setenv.sh
aws ecr describe-repositories --repository-names ${IMAGE_NAME}
if [ $? -ne 0 ]
then
 aws ecr create-repository --repository-name learn-sb-ecs
fi
docker push ${ECR_IMAGE_NAME}



