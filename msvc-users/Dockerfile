FROM openjdk:17 AS builder

WORKDIR /app/msvc-users

#COPY ./target/msvc-users-0.0.1-SNAPSHOT.jar .
COPY ./pom.xml /app
COPY ./msvc-users/.mvn ./.mvn
COPY ./msvc-users/mvnw .
COPY ./msvc-users/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/



COPY ./msvc-users/src ./src

RUN ./mvnw clean package -DskipTests


#usando el builder para hacer solo lo tomar el jar y no las dependencias
FROM openjdk:17

WORKDIR /app


COPY --from=builder /app/msvc-users/target/*.jar msvc-users.jar


EXPOSE 8001

ENTRYPOINT ["java","-jar","msvc-users.jar"]
#CMD ["java","-jar","msvc-users.jar"]

#
# Etapa de construcción
