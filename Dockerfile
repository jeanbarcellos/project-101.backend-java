FROM gradle:7.5.1-jdk11-alpine
# FROM gradle:7.0.0-jdk11 AS build
COPY --chown=gradle:gradle . .
WORKDIR .
RUN gradle build -x test --no-daemon
EXPOSE 8080
CMD ["gradle", "bootRun"]