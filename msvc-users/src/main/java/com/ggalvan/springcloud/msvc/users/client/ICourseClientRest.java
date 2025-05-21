package com.ggalvan.springcloud.msvc.users.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

//spring.application.name=courses
//server.port=8002
//@FeignClient(name = "courses", url = "localhost:8002/api/courses/")
//@FeignClient(name = "courses", url = "host.docker.internal:8002/api/courses/")

@FeignClient(name = "msvc-courses", url = "msvc-courses:8002/api/courses/")
public interface ICourseClientRest {

    @DeleteMapping("/delete-user-course/{id}")
    void deleteCourseUserById(@PathVariable Long id);
}
