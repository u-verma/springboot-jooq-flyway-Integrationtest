FROM openjdk:17-alpine

LABEL maintainer="Umesh Verma"

RUN mkdir /application
WORKDIR /application

COPY build/libs/*.jar ./application.jar

EXPOSE 8080

ARG JAVA_ADDITIONAL_OPTS
ENV JAVA_ADDITIONAL_OPTS=$JAVA_ADDITIONAL_OPTS
ENTRYPOINT java $JAVA_ADDITIONAL_OPTS \
                -Djava.security.egd=file:/dev/./urandom \
                -jar application.jar
