package com.ggalvan.springcloud.msvc.cursos.repositories;

import com.ggalvan.springcloud.msvc.cursos.models.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface ICourseRepository extends CrudRepository<Course, Long> {
}
