#!/usr/bin/env bash

GREEN='\033[0;32m'
NC='\033[0m'

echo -e "${GREEN} -- build and deploy service to CF  -- ${NC}"
./gradlew clean build
cf push

echo -e "${GREEN} -- enabling service access for virusscanner service  -- ${NC}"
cf enable-service-access virusscanner
echo -e "${GREEN} -- creating service virusscanner with a free plan  -- ${NC}"
cf create-service virusscanner free free-virusscanner