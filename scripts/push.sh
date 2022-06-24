#!/bin/bash

services=(account-svc email-svc planck web)

for service in "${services[@]}"
do
  cd "$service" || exit 1
  image_name="$DOCKER_REPO/$service:latest"
  docker build -t $image_name .
  cd ..
done
