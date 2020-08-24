package com.notes.service;

import com.notes.model.User;

import java.util.Optional;

public interface UserService {

    void save(User user);
    Optional<User> findByUsername(String username);
}