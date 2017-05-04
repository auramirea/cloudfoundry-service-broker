#!/usr/bin/env bash

RED='\033[0;31m'
NC='\033[0m'

echo -e "${RED} -- building service broker  -- ${NC}"
./gradlew build
echo -e "${RED} -- login in to cf to target the space and org -- ${NC}"
cf login --skip-ssl-validation -a "https://api.local.pcfdev.io" -u admin -p admin -o pcfdev-org -s pcfdev-space
echo -e "${RED} -- cf push the broker  -- ${NC}"
cf push
echo -e "${RED} -- creating service broker  -- ${NC}"
cf create-service-broker generic-service-broker admin admin http://generic-service-broker.local.pcfdev.io

echo -e "${RED} -- changing directory to service folder and executing install and cf push  -- ${NC}"
cd service/
mvn install& cf push

echo -e "${RED} -- enabling service access for virusscanner service  -- ${NC}"
cf enable-service-access virusscanner
echo -e "${RED} -- creating service virusscanner with a free plan  -- ${NC}"
cf create-service virusscanner free free-virusscanner

echo -e "${RED} -- changing directory to client and executing install and cf push  -- ${NC}"
cd ../client
mvn install & cf push
