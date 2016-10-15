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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
        List<Role> availableRoles = bootstrapApplicationRoles();
        log.info("Available roles: " + roleRepository.findAll());

        User test = new UserBuilder()
                .setUsername("test@sep.se")
                .setPassword("test")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                         UserRole.ADMIN.roleName()
                        ,UserRole.VICE_PRESIDENT.roleName()
                )))
                .build();
        userService.save(test);



        /*
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
         */
        log.info("ADDED 'test@sep.se' IDENTIFIED BY 'test'");
    }

    private List<Role> bootstrapApplicationRoles() {
        List<Role> allRoles = new ArrayList<>();

        for (UserRole role : UserRole.values()) {
            Role newRole = new Role();
            newRole.setName(role.roleName());
            allRoles.add(newRole);
            roleRepository.save(newRole);
            log.info("Created '" + role.name() + "' role");
        }
        return allRoles;
    }
}
