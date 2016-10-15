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
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class UserLoader implements ApplicationListener<ContextRefreshedEvent>, Ordered {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    private Logger log = Logger.getLogger(UserLoader .class);

    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Role> availableRoles = bootstrapApplicationRoles();
        log.info("Available roles: " + availableRoles);

        // TEST USERS
        User test = new UserBuilder()
                .setName("Test User")
                .setUsername("test@sep.se")
                .setPassword("test")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                         UserRole.ADMIN.roleName()
                )))
                .build();
        userService.save(test);

        // FINANCE
        User finAlice = new UserBuilder()
                .setName("Alice")
                .setUsername("fin-alice@sep.se")
                .setPassword("fin")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.FINANCIAL_MANAGER.roleName()
                )))
                .build();
        userService.save(finAlice);

        // CUSTOMER SERVICE TEAM
        User csMike = new UserBuilder()
                .setName("Mike")
                .setUsername("admin-mike@sep.se")
                .setPassword("admin")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.ADMIN_MANAGER.roleName()
                )))
                .build();
        userService.save(csMike);
        User csSarah = new UserBuilder()
                .setName("Sarah")
                .setUsername("cs-sarah@sep.se")
                .setPassword("cs")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.CUSTOMER_SERVICE.roleName()
                )))
                .build();
        userService.save(csSarah);
        User csSam = new UserBuilder()
                .setName("Sam")
                .setUsername("cs-sam@sep.se")
                .setPassword("cs")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.CUSTOMER_SERVICE.roleName()
                )))
                .build();
        userService.save(csSam);
        User csJudy = new UserBuilder()
                .setName("Judy")
                .setUsername("cs-judy@sep.se")
                .setPassword("cs")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.CUSTOMER_SERVICE.roleName()
                )))
                .build();
        userService.save(csJudy);
        User csmJanet = new UserBuilder()
                .setName("Janet")
                .setUsername("csm-janet@sep.se")
                .setPassword("csm")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.CUSTOMER_SERVICE.roleName(),
                        UserRole.CUSTOMER_SERVICE_MANAGER.roleName(),
                        UserRole.VICE_PRESIDENT.roleName()

                )))
                .build();
        userService.save(csmJanet);

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
