package com.ggalvan.springcloud.msvc.cursos.services;

import com.ggalvan.springcloud.msvc.cursos.models.User;
import com.ggalvan.springcloud.msvc.cursos.models.entity.Course;

import java.util.List;
import java.util.Optional;

public interface ICourseService {

    List<Course> findAll();
    Optional<Course> findById(Long id);
    Optional<Course> findByWithUsers(Long id);
    Course saveCourse(Course course);

    void delete(Long id);


//    ? methods course users

    Optional<User> userAsign(User user, Long courseId);
    Optional<User> userCreate(User user, Long courseId);

    Optional<User> removeUserCourse(User user, Long courseId);
    void deleteCourseUserById(Long id);
}
