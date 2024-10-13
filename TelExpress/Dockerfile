FROM openjdk:24-ea-17-jdk
VOLUME /tmp
EXPOSE 8080
ADD ./target/TelExpress-0.0.1-SNAPSHOT.jar grupo2.jar

LABEL authors="Isaac"

ENTRYPOINT ["java", "-jar","grupo2.jar"]