# MedicineScheduler
## Set up
## Data base
0. https://github.com/mysql/mysql-docker
1. docker pull mysql/mysql-server:5.7
2. docker run --detach --name=mysql1 --env="MYSQL_ROOT_HOST=%" --publish 3306:3306 mysql/mysql-server:5.7
3. docker logs mysql1 2>&1 | grep GENERATED
4. docker exec -it mysql1 mysql -uroot -p (use password from 4)
5. ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
6. ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'root';
7. FLUSH PRIVILEGES;
8. CREATE DATABASE test_db;
9. add entities

## App
1. docker build -t test -f deployment/docker/app.Dockerfile .
2. docker run --name test --network=host --user chrome --privileged -d test   