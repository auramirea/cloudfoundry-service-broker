#!/usr/bin/env bash

GREEN='\033[0;32m'
NC='\033[0m'

echo -e "${GREEN} -- build and deploy client to CF  -- ${NC}"
./gradlew clean build
cf push