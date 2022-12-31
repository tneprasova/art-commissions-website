#! /bin/bash

chmod u+x server/gradlew
chmod u+x client/tjv-semestral-work-client/gradlew

cd server
./gradlew bootBuildImage
cd ../client/tjv-semestral-work-client
./gradlew bootBuildImage
cd ../../

docker-compose -f docker-compose.yml up