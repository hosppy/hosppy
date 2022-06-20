#!/bin/bash

services=(account-svc email-svc planck web)

for service in "${services[@]}"
do
  cd "$service" || exit 1
  image_name="$DOCKER_REPO/$service"
  docker build -t $image_name .
  docker push $image_name
  docker rmi $image_name
  cd ..
done

docker rmi "$(docker images | grep "none" | awk '{print $3}')"