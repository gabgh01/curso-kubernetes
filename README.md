
# Proyecto Curso Microservicios Usando Spring Boot Con Docker y Kubernetes by Andres Guzman 

Este es proyecto es el resultado de la practica y guia del seguimiento del curso impartido en Udemy por el profesor Andres Garcia, el cual he realizado unas muy pequeñas modificaciones en el tema del lenguaje que esta escrito en ingles, nombres de endpoint y metodos, le he agregado algo de documentacion en swagger pero las estructura sigue el reflejo de las enseñansas y la direcciòn del curso.

comando para correr imagen ya habiendo creado la red spring

comando para crear red: docker network create spring

comando para ejecutar contenedor con red:

docker run -p 8001:8001 -d --rm --name msvc-users --network spring msvc-users