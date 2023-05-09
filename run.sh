#! /bin/bash

chmod u+x server/gradlew
chmod u+x client/gradlew

echo "BUILD SERVER"
cd server
./gradlew bootBuildImage
echo "BUILD CLIENT"
cd ../client
./gradlew bootBuildImage
cd ../

docker-compose up -d