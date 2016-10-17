package com.sep.services;


import com.sep.domain.Role;
import com.sep.domain.User;
import com.sep.repositories.RoleRepository;
import com.sep.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Set<User> findByRoleNames(Set<String> roleNames) {
        Set<User> results = new HashSet<>();
        List<User> all = userRepository.findAll();
        for (User it : all) {
            if (hasRoles(it, roleNames)) {
                log.info("User " + it.getName() + " has the required perms");
                results.add(it);
            }
        }
        return results;
    }


    public boolean hasRoles(User it, Set<String> roleNames) {
        // Extract his/her roles as strings
        Set<String> strRoles = new HashSet<>();
        for (Role role : it.getRoles()) {
            strRoles.add(role.getName());
        }
        // Compare
        for (String test : roleNames) {
            if (!strRoles.contains(test)) {
                return false;
            }
        }
        return true;
    }
}