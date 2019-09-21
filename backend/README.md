### Carina's BackEnd

- Build backend with Spring Boot (OpenJDK v11.0.4 and Junit 4)
- Build Spring Boot with Gradle (v5.2.1)
- Store data in MYSQL (v5.5)
- Pull live data from LTA DataMall with AWS Lambda
- Host on AWS
- Test and build on Ubuntu 18

### Manual Setup of MongoDB

- Enter MySQL
```$xslt
sudo mysql
```
- Create user
```$xslt
CREATE USER 'lta'@'localhost' IDENTIFIED BY 'lta';
GRANT ALL PRIVILEGES ON * . * TO 'lta'@'localhost';
FLUSH PRIVILEGES;
```

### Run the backend
- Install Java 11 and Gradle 5+
- Execute the command
```$xslt
./gradlew bootRun
```
- Direct browser to http://localhost:8080/carpark-availability
- Json data of the parking lots from database should appear

### Swagger API
- Point browser to http://localhost:8080/swagger-ui.html to view endpoints

### Unit and Integeration Test
- Run integration and unit test
```$xslt
./gradlew test
```
- Check the code style
```$xslt
./gradlew check
```

### Resources

- https://www.codementor.io/gtommee97/rest-api-java-spring-boot-and-mongodb-j7nluip8d
- https://www.baeldung.com/jsonassert
- https://www.tutorialspoint.com/spring_boot/spring_boot_enabling_swagger2.htm
- https://www.nexsoftsys.com/articles/how-to-deploy-spring-boot-application-in-aws-ec2-server-quickly.html
- https://geekyneha.wordpress.com/2018/11/17/how-to-deploy-spring-boot-application-on-aws-ec2/
- https://www.callicoder.com/spring-boot-spring-security-jwt-mysql-react-app-part-1/
- https://www.callicoder.com/spring-boot-security-oauth2-social-login-part-1/
- https://spring.io/guides/gs/accessing-data-mysql/
- https://www.youtube.com/watch?v=QBFFs2m5m94
- https://www.linode.com/docs/development/java/how-to-deploy-spring-boot-applications-nginx-ubuntu-16-04/