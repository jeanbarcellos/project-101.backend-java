FROM openjdk:11
# FROM openjdk:11-jre
# FROM adoptopenjdk/openjdk11:alpine-jre

VOLUME /tmp

EXPOSE 8080 8081

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]