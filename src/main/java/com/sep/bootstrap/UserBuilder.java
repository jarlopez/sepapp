package com.sep.bootstrap;

import com.sep.domain.Role;
import com.sep.domain.User;

import java.util.Set;

public class UserBuilder {
    private User user;

    public UserBuilder() {
        this.user = new User();
    }

    public UserBuilder setUsername(String username) {
        this.user.setUsername(username);
        return this;
    }
    public UserBuilder setPassword(String password) {
        this.user.setPassword(password);
        return this;
    }
    public UserBuilder setRoles(Set<Role> roles) {
        this.user.setRoles(roles);
        return this;
    }
    public UserBuilder addRole(Role role) {
        this.user.addToRoles(role);
        return this;
    }

    public User build() {
        return this.user;
    }
}
