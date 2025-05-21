FROM openjdk:17 AS builder

WORKDIR /app/msvc-courses

#COPY ./target/msvc-users-0.0.1-SNAPSHOT.jar .
COPY ./pom.xml /app
COPY ./cursos/.mvn ./.mvn
COPY ./cursos/mvnw .
COPY ./cursos/pom.xml .

#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
RUN ./mvnw dependency:go-offline


COPY ./cursos/src ./src

RUN ./mvnw clean package -DskipTests


#usando el builder para hacer solo lo tomar el jar y no las dependencias
FROM openjdk:17

WORKDIR /app


COPY --from=builder /app/msvc-courses/target/*.jar msvc-courses.jar


EXPOSE 8002

ENTRYPOINT ["java","-jar","msvc-courses.jar"]
#CMD ["java","-jar","msvc-users.jar"]

#
# Etapa de construcción
