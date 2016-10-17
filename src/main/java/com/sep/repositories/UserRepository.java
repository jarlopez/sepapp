package com.sep.repositories;

import com.sep.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByRoles_Name(List<String> roles);
    List<User> findByRoles_Id(List<Long> ids);
}