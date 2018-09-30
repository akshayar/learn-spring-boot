#!/bin/bash
. ./setenv.sh
mkdir -p ${PROJECT_ROOT}/target/aws/
envsubst < aws/task.json > ${PROJECT_ROOT}/target/aws/task.json
cat ${PROJECT_ROOT}/target/aws/task.json

aws ecs register-task-definition --cli-input-json file://${PROJECT_ROOT}/target/aws/task.json
aws ecs run-task --task-definition ${TASK_NAME} --region ${AWS_REGION_NAME} --cluster ${CLUSTER_NAME} --network-configuration  "awsvpcConfiguration={subnets=["${SUBNET}"],securityGroups=["${SECURITY_GROUP}"],assignPublicIp="ENABLED"}" --launch-type FARGATE




