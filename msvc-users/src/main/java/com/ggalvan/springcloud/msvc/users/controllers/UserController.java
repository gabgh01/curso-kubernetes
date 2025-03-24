package com.ggalvan.springcloud.msvc.users.controllers;

import com.ggalvan.springcloud.msvc.users.models.entity.User;
import com.ggalvan.springcloud.msvc.users.services.IUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/",""})
    public ResponseEntity<ApiResponse> showUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.name(), userService.fidAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserBy(@PathVariable Long id){
//        Optional<User> user = userService.findById(id);
//        return user.isPresent()?ResponseEntity.ok(user.get()):ResponseEntity.notFound().build();
        return userService.findById(id)
                .<ResponseEntity<Object>>map(user -> ResponseEntity.ok().body(user))  // Retorna un User si existe
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "Usuario no encontrado!!!")));
//                .map(user -> ResponseEntity.ok(user))  // Devuelve 200 OK con el User
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error","error")));
    }

    @PostMapping
    // * forma 1
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?>  save(@RequestBody User user){
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody User user, @PathVariable Long id){
        Optional<User> userOptional = userService.findById(id);
        if(userOptional.isPresent()){
            User userDb = userOptional.get();
//            *edit user object
            userDb.setName(user.getName());
            userDb.setPassword(user.getPassword());
            userDb.setEmail(user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body( userService.save(userDb));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.name(), null));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){

        Optional<User> userOptional = userService.findById(id);
        if(userOptional.isPresent()){
            userService.delete(id);
            User user = userOptional.get();

            log.info("user: {}",user);

            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("Error","no se pudo eliminar"));
    }

    @PostMapping("/course-users")
    public ResponseEntity<?> getFindAllUserIds(@RequestBody List<Long> listIds){
        return ResponseEntity.ok(userService.findAllById(listIds));
    }



}
