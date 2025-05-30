package com.ggalvan.springcloud.msvc.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CoursesApplication {

    public static void main(String[] args) {
		SpringApplication.run(CoursesApplication.class, args);
    }

}
