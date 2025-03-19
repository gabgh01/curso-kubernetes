package com.ggalvan.springcloud.msvc.users.services;

import com.ggalvan.springcloud.msvc.users.models.entity.User;
import com.ggalvan.springcloud.msvc.users.repositories.IUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ImplUserService implements IUserService {

    private final IUserRepository userRepository;

    public ImplUserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> fidAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAllById(Iterable<Long> listIds) {
        return (List<User>) userRepository.findAllById(listIds);
    }
}
