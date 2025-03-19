package com.ggalvan.springcloud.msvc.cursos.clients;

import com.ggalvan.springcloud.msvc.cursos.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "msvc-users", url = "localhost:8001/api/users/")
public interface IUserClientRest {

    @GetMapping("/{id}")
    public User findUser(@PathVariable Long id);
    @PostMapping()
    public User create(@RequestBody User user);

    @PostMapping("/course-users")
    List<User> findAllUserByCourse(@RequestBody Iterable<Long> ids);
}
