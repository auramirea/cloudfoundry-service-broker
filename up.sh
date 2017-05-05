#!/usr/bin/env bash

GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

orgs=$(cf orgs)
if [[ " ${orgs[@]} " =~ "demo-org" ]]; then
    echo -e "${RED}demo-org already present on CF instance. Exiting demo script."
    echo -e "If you want to run this script, run the tear down script \"./down.sh\" first.${NC}"
    exit;
fi

echo -e "${GREEN} -- login in to local pcf installation at \"https://api.local.pcfdev.io\"  -- ${NC}"
yes "" | cf login --skip-ssl-validation -a "https://api.local.pcfdev.io" -u admin -p admin

echo -e "${GREEN} -- creating org for demo and targeting it for deploy  -- ${NC}"
cf create-org demo-org && cf target -o demo-org

echo -e "${GREEN} -- creating space for demo  -- ${NC}"
cf create-space demo-space && cf target -s demo-space

echo -e "${GREEN} -- build and cf push the broker  -- ${NC}"
./gradlew build
cf push

echo -e "${GREEN} -- creating service broker  -- ${NC}"
cf create-service-broker generic-service-broker admin admin http://generic-service-broker.local.pcfdev.io

echo -e "${GREEN} -- changing directory to service folder and executing install and cf push  -- ${NC}"
cd service/
./mvnw clean install
cf push

echo -e "${GREEN} -- enabling service access for virusscanner service  -- ${NC}"
cf enable-service-access virusscanner
echo -e "${GREEN} -- creating service virusscanner with a free plan  -- ${NC}"
cf create-service virusscanner free free-virusscanner

echo -e "${GREEN} -- changing directory to client and executing install and cf push  -- ${NC}"
cd ../client
./mvnw clean install
cf push
cd ..
