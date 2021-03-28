FROM openjdk:11

ARG PROFILE
ARG ADDTIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDTIONAL_OPTS=${ADDTIONAL_OPTS}

WORKDIR /opt/eng-zap-challenge

COPY /target/eng-zap-challenge*.jar zap-challenge.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 8080
EXPOSE 5005

CMD java ${ADDTIONAL_OPTS} -jar zap-challenge.jar --spring.profile.active=${PROFILE}