FROM eclipse-temurin:21
ARG JAR=target/*.jar
RUN mkdir /target
COPY ${JAR} /target/forum.jar
ENTRYPOINT ["java", "-jar", "/target/forum.jar"]

