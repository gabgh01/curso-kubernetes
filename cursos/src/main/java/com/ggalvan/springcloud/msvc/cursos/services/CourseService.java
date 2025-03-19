package com.ggalvan.springcloud.msvc.cursos.services;

import com.ggalvan.springcloud.msvc.cursos.clients.IUserClientRest;
import com.ggalvan.springcloud.msvc.cursos.models.User;
import com.ggalvan.springcloud.msvc.cursos.models.entity.Course;
import com.ggalvan.springcloud.msvc.cursos.models.entity.CourseUser;
import com.ggalvan.springcloud.msvc.cursos.repositories.ICourseRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Data
@Slf4j
//@AllArgsConstructor
//@RequiredArgsConstructor
public class CourseService implements ICourseService {
    private final ICourseRepository courseRepository;
    private final IUserClientRest userClientRest;

    public CourseService(ICourseRepository courseRepository, IUserClientRest userClientRest) {
        this.courseRepository = courseRepository;
        this.userClientRest = userClientRest;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findByWithUsers(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if(optionalCourse.isPresent()){
            Course course = optionalCourse.get();
            log.info("Informacion de course: {}",course);
            if(!course.getCourseUsers().isEmpty()){
                List<Long> ids = course.getCourseUsers().stream().map(CourseUser::getUserId).toList();
                log.info("lista ids: {}",ids);
                List<User> users = userClientRest.findAllUserByCourse(ids);
                course.setUserList(users);

            }
            return Optional.of(course);
        }
        return Optional.empty();
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void delete(Long id) {

        courseRepository.deleteById(id);

    }

    @Override
    @Transactional
    public Optional<User> userAsign(User user, Long courseId) {
//        ?consultando si existe curso id
        Optional<Course> o = courseRepository.findById(courseId);
        if (o.isPresent()) {
            User userMsvc = userClientRest.findUser(user.getId());
            log.info("information user {}", userMsvc.toString());
            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(user.getId());
            course.addCourseUser(courseUser);
            courseRepository.save(course);
            //** return user
            return Optional.of(user);

        }
        return Optional.empty();
    }


    @Override
    @Transactional
    public Optional<User> userCreate(User user, Long courseId) {
        Optional<Course> o = courseRepository.findById(courseId);
        if (o.isPresent()) {
            User userMsvc = userClientRest.create(user);
            log.info("information user {}", userMsvc.toString());
            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());
            course.addCourseUser(courseUser);
            courseRepository.save(course);
            //** return user
            return Optional.of(userMsvc);

        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> removeUserCourse(User user, Long courseId) {
        Optional<Course> o = courseRepository.findById(courseId);
        if (o.isPresent()) {
            User userMsvc = userClientRest.findUser(user.getId());
            log.info("information user {}", userMsvc.toString());
            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());


            course.removeCourseUser(courseUser);
            courseRepository.save(course);
            //** return user
            return Optional.of(userMsvc);

        }
        return Optional.empty();
    }
}
