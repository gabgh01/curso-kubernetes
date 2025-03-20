package com.ggalvan.springcloud.msvc.cursos.controllers;

import com.ggalvan.springcloud.msvc.cursos.models.User;
import com.ggalvan.springcloud.msvc.cursos.models.entity.Course;
import com.ggalvan.springcloud.msvc.cursos.services.ICourseService;
import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Course Controller", description = "API for courses administrator")
@Data
@Slf4j
//@RequiredArgsConstructor
public class CourseController {

    private final ICourseService courseService;

    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Obtiene todos los cursos", description = "Devuelve una lista de cursos disponibles")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista de cursos obtenida correctamente")})
    @GetMapping({"/", ""})
    public ResponseEntity<List<Course>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getId(@PathVariable Long id) {
        System.out.println("curso opcional: " + courseService.findByWithUsers(id));
        Optional<Course> optionalCourse = courseService.findByWithUsers(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            return ResponseEntity.status(HttpStatus.OK).body(course);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Crea un curso", description = "Crea un nuevo curso y lo devuelve")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Curso creado exitosamente")})

    @PostMapping("/")
    public ResponseEntity<?> create(@Parameter(description = "Datos del curso", required = true) @Valid @RequestBody Course course, BindingResult result) {
        ResponseEntity<Map<String, String>> errors = getErrors(result);
        if (errors != null) return errors;

        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(course));
    }

    private ResponseEntity<Map<String, String>> getErrors(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), String.format("Field %s %s", error.getField(), error.getDefaultMessage()));
            });
            return ResponseEntity.badRequest().body(errors);
        }
        return null;
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody Course course) {
        Optional<Course> optionalCourse = courseService.findById(id);

        if (optionalCourse.isPresent()) {
            Course courseDb = optionalCourse.get();
            courseDb.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(courseDb));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

//        Optional<Course> optionalCourse = courseService.findById(id);
        Optional<Course> optionalCourse = courseService.findById(id);
        if (optionalCourse.isPresent()) {
            courseService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/asign-user/{courseId}")
    public ResponseEntity<?> asignUser(@RequestBody User user, @PathVariable Long courseId) {
        log.info("Usuario {}", user.toString());
        Optional<User> userOptional;
        try {

            userOptional = courseService.userAsign(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Error: not find userId communication error  " + e.getMessage()));
        }

        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userOptional.get());
        }

        return ResponseEntity.notFound().build();


    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> userOptional;
        try {
            userOptional = courseService.userCreate(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Not user create or error communication: " + e.getMessage()));
        }

        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userOptional.get());
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/delete-user/{courseId}")
    public ResponseEntity<?> removeUserCourse(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> userOptional;
        try {
            userOptional = courseService.removeUserCourse(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Not user create or error communication: " + e.getMessage()));
        }

        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/delete-user-course/{id}")
    public ResponseEntity<?> deleteUserCourseById(@PathVariable Long id) {
        courseService.deleteCourseUserById(id);
        return ResponseEntity.noContent().build();
    }


}
