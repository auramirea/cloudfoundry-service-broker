#!/usr/bin/env bash

GREEN='\033[0;32m'
NC='\033[0m'

echo -e "${GREEN} -- initiating clean up  -- ${NC}"

cf unbind-service demoFileUploader free-virusscanner
cf delete-service free-virusscanner -f
cf delete-service virusscanner-service -f
cf delete-service-broker generic-service-broker -f
cf delete-route local.pcfdev.io --hostname demofileuploader -f
cf delete-route local.pcfdev.io --hostname virusscanner-service -f
cf delete-route local.pcfdev.io --hostname generic-service-broker -f

echo -e "${GREEN} -- deleting org for demo  -- ${NC}"
yes y | cf delete-org demo-org
