#!/usr/bin/env bash

GREEN='\033[0;32m'
NC='\033[0m'

echo -e "${GREEN} -- deleting org for demo  -- ${NC}"
yes y | cf delete-org demo-org

echo -e "${GREEN} -- building service broker  -- ${NC}"
./gradlew build
echo -e "${GREEN} -- login in to local pcf installation at \"https://api.local.pcfdev.io\"  -- ${NC}"
yes "" | cf login --skip-ssl-validation -a "https://api.local.pcfdev.io" -u admin -p admin

echo -e "${GREEN} -- creating org for demo and targeting it for deploy  -- ${NC}"
cf create-org demo-org && cf target -o demo-org

echo -e "${GREEN} -- creating space for demo  -- ${NC}"
cf create-space demo-space && cf target -s demo-space
echo -e "${GREEN} -- target space to push demo apps  -- ${NC}"
cf target -o demo-org -s demo-space

echo -e "${GREEN} -- clean-up -- ${NC}"
cf delete-service free-virusscanner -f
cf delete-service-broker generic-service-broker -f
cf delete-route local.pcfdev.io --hostname demofileuploader -f
cf delete-route local.pcfdev.io --hostname virusscanner-service -f
cf delete-route local.pcfdev.io --hostname generic-service-broker -f

echo -e "${GREEN} -- build and cf push the broker  -- ${NC}"
./gradlew build
cf push

echo -e "${GREEN} -- creating service broker  -- ${NC}"
cf create-service-broker generic-service-broker admin admin http://generic-service-broker.local.pcfdev.io

echo -e "${GREEN} -- changing directory to service folder and executing install and cf push  -- ${NC}"
cd service/
./mvnw install
cf push

echo -e "${GREEN} -- enabling service access for virusscanner service  -- ${NC}"
cf enable-service-access virusscanner
echo -e "${GREEN} -- creating service virusscanner with a free plan  -- ${NC}"
cf create-service virusscanner free free-virusscanner

echo -e "${GREEN} -- changing directory to client and executing install and cf push  -- ${NC}"
cd ../client
./mvnw install
cf push


