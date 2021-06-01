package com.maltsevve.restApi.service;

import com.maltsevve.restApi.model.User;
import com.maltsevve.restApi.repository.JavaIOUserRepositoryImpl;
import com.maltsevve.restApi.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository = new JavaIOUserRepositoryImpl();

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.update(user);
    }

    public User getById(Long aLong) {
        return userRepository.getById(aLong);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public void deleteById(Long aLong) {
        userRepository.deleteById(aLong);
    }
}
