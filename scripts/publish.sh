#!/bin/bash

#docker login -u "$DOCKER_USERNAME" -p "$DOCKER_PASSWORD" "$DOCKER_REGISTRY"

services=(account-svc email-svc planck)
for service in "${services[@]}"
do
  cd "$service" || exit 1
  image_name="$DOCKER_REPO/$service"
  tag_commit=$(git rev-parse --short HEAD)
  docker build -t "$image_name" .
  docker tag "$image_name" "$image_name:$tag_commit"
#  docker push "$image_name:latest"
#  docker push "$image_name:$tag_commit"
  cd ..
done
