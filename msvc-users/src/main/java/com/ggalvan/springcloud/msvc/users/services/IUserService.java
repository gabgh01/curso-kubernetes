package com.ggalvan.springcloud.msvc.users.services;

import com.ggalvan.springcloud.msvc.users.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> fidAll();

    Optional<User> findById(Long id);
    User save(User user);
    void delete(Long id);

    List<User> findAllById(Iterable<Long> listIds);

}
