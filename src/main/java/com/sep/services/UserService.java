package com.sep.services;

import com.sep.domain.User;

import java.util.Set;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    Set<User> findByRoleNames(Set<String> roleNames);

}