package com.ggalvan.springcloud.msvc.cursos.models.entity;

import com.ggalvan.springcloud.msvc.cursos.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
//@Data
//@RequiredArgsConstructor
//estas tambiem son las denominadas DTO
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del curso, autogenerado por la base de datos", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @NotBlank(message = "El nombre del curso es obligatorio")
    @Schema(description = "Nombre del curso", example = "Java Avanzado", required = true)
    @Column(nullable = false)
    @NotEmpty(message = "Error: field name is empty")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<CourseUser> courseUsers;
    @Transient
    private List<User> userList;

    public Course() {
        this.courseUsers = new ArrayList<>();
        userList = new ArrayList<>();
    }

    public Course(Long id, String name) {
        this.id = id;
        this.name = name;
        courseUsers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CourseUser> getCourseUsers() {
        return courseUsers;
    }

    public void setCourseUsers(List<CourseUser> courseUsers) {
        this.courseUsers = courseUsers;
    }

    public void addCourseUser(CourseUser courseUser) {
        this.courseUsers.add(courseUser);

    }

    public void removeCourseUser(CourseUser courseUser) {
        this.courseUsers.remove(courseUser);

    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
