#!/usr/bin/env bash

GREEN='\033[0;32m'
NC='\033[0m'

./cf-setup.sh
[[ $? -ne 0 ]] && exit
./broker-deploy.sh
echo -e "${GREEN} -- changing directory to service -- ${NC}"
cd service/
./service-deploy.sh

echo -e "${GREEN} -- changing directory to client -- ${NC}"
cd ../client
./client-deploy.sh
cd ..
exit 0