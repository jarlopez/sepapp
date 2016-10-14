package com.sep.services;

import com.sep.domain.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}