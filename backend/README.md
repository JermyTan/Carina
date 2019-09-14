### Carina's BackEnd

- Build backend with Spring Boot (OpenJDK v11.0.4 and Junit 4)
- Build Spring Boot with Gradle (v5.2.1)
- Store data in MongoDB (v3.6.3)
- Pull live data from LTA DataMall with AWS Lambda
- Host on AWS
- Test and build on Ubuntu 18

### Manual Setup of MongoDB

- Enter MongoDB
```$xslt
mongo
```
- Create and use database
```$xslt
use carina;
```
- Create table
```$xslt
db.createCollection("carparkAvailability");
```
- Add data to table
```$xslt
db.carparkAvailability.insertMany([ {"CarParkID":"2","Area":"Marina","Development":"Marina Square","Location":"1.29115 103.85728","AvailableLots":1838,"LotType":"C","Agency":"LTA"},{"CarParkID":"3","Area":"Marina","Development":"Raffles City","Location":"1.29382 103.85319","AvailableLots":922,"LotType":"C","Agency":"LTA"},{"CarParkID":"4","Area":"Marina","Development":"The Esplanade","Location":"1.29011 103.85561","AvailableLots":795,"LotType":"C","Agency":"LTA"},{"CarParkID":"5","Area":"Marina","Development":"Millenia Singapore","Location":"1.29251 103.86009","AvailableLots":1217,"LotType":"C","Agency":"LTA"} ])
```
- Check data in table
```$xslt
db.carparkAvailability.find({});
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
- https://stackoverflow.com/questions/13312358/mongo-couldnt-connect-to-server-127-0-0-127017
- https://www.tutorialspoint.com/spring_boot/spring_boot_enabling_swagger2.htm
- https://www.baeldung.com/spring-boot-embedded-mongodb
- https://stackoverflow.com/questions/40580088/switch-between-mongo-production-and-test-database-java-spring-boot
- https://www.nexsoftsys.com/articles/how-to-deploy-spring-boot-application-in-aws-ec2-server-quickly.html
- https://geekyneha.wordpress.com/2018/11/17/how-to-deploy-spring-boot-application-on-aws-ec2/
- https://www.callicoder.com/spring-boot-spring-security-jwt-mysql-react-app-part-1/
- https://www.callicoder.com/spring-boot-security-oauth2-social-login-part-1/
- https://spring.io/guides/gs/accessing-data-mysql/
- https://stormpath.com/blog/secure-spring-boot-webapp-apache-letsencrypt-ssl
- https://www.youtube.com/watch?v=QBFFs2m5m94
- https://www.linode.com/docs/development/java/how-to-deploy-spring-boot-applications-nginx-ubuntu-16-04/