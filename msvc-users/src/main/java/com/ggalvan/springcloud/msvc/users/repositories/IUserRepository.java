package com.ggalvan.springcloud.msvc.users.repositories;

import com.ggalvan.springcloud.msvc.users.models.entity.User;
import org.springframework.data.repository.CrudRepository;


public interface IUserRepository extends CrudRepository<User, Long> {




}
