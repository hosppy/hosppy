#!/bin/bash

services=(account-svc email-svc planck web)
for service in "${services[@]}"
do
  cd "$service" || exit 1
  image_name="$DOCKER_REPO/$service"
  tag_commit=$(git rev-parse --short HEAD)
  docker build -t "$image_name" . || exit 2
  docker tag "$image_name" "$image_name:$tag_commit"
  docker tag "$image_name" "localhost:32000/$service:latest"
  docker push "localhost:32000/$service:latest"
  cd ..
done

