#!/usr/bin/env bash

GREEN='\033[0;32m'
NC='\033[0m'

echo -e "${GREEN} -- build and deploy service broker to CF  -- ${NC}"
../gradlew build
cf push

echo -e "${GREEN} -- creating service broker  -- ${NC}"
cf create-service-broker generic-service-broker admin admin http://generic-service-broker.local.pcfdev.io

echo -e "${GREEN} -- enabling service access for virusscanner service  -- ${NC}"
cf enable-service-access virusscanner

echo -e "${GREEN} -- creating service virusscanner with a free plan  -- ${NC}"
cf create-service virusscanner free free-virusscanner

