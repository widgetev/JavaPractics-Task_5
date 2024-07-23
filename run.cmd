mvn clean compile package -Dmaven.test.skip
docker-compose build --no-cache --pull
docker-compose up -d