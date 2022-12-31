#! /bin/bash

chmod u+x server/gradlew
chmod u+x client/tjv-semestral-work-client/gradlew

cd server
./gradlew bootBuildImage
cd ../client/tjv-semestral-work-client
./gradlew bootBuildImage
cd ../../

if [ "$1" = "local" ];
then
    docker-compose -f docker-compose-local.yml up
    cd server/src/main/resources/test.rest
else
    docker-compose -f docker-compose.yml up
fi