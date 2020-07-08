FROM adoptopenjdk/openjdk14:jre-14.0.1_7-alpine
ADD build/libs/starling-roundup.jar /app.jar
ENV BASIC_AUTH_ENABLED true
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]