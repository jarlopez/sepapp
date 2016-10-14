package com.sep.bootstrap;

import com.sep.common.UserRole;
import com.sep.domain.Role;
import com.sep.domain.User;
import com.sep.repositories.RoleRepository;
import com.sep.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserLoader implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Logger log = Logger.getLogger(UserLoader .class);

    public void onApplicationEvent(ContextRefreshedEvent event) {
        bootstrapApplicationRoles();
        log.info("Available roles: " + roleRepository.findAll());

        User admin = new User();
        admin.setUsername("admin@sep.se");
        admin.setPassword("admin");
        admin.setPasswordConfirm("admin");
        userService.save(admin);

        /*
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
         */
        log.info("ADDED 'admin@sep.se' IDENTIFIED BY 'admin'");
    }

    private void bootstrapApplicationRoles() {
        for (UserRole role : UserRole.values()) {
            Role newRole = new Role();
            newRole.setName(role.name());
            roleRepository.save(newRole);
            log.info("Created '" + role.name() + "' role");
        }
    }
}
