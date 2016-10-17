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
        List<String> allRoles = new ArrayList<>();
        for (UserRole it : UserRole.values()) {
            allRoles.add(it.roleName());
        }
        User test = new UserBuilder()
                .setName("Superuser")
                .setUsername("johan-sudo@sep.se")
                .setPassword("sudo")
                .setRoles(roleRepository.findByNames(allRoles))
                .build();
        userService.save(test);

        // FINANCE
        User finManager = new UserBuilder()
                .setName("Johan (finance manager)")
                .setUsername("fin@sep.se")
                .setPassword("fin")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.FINANCIAL_MANAGER.roleName()
                )))
                .build();
        userService.save(finManager);
        User finAlice = new UserBuilder()
                .setName("Alice")
                .setUsername("fin-alice@sep.se")
                .setPassword("fin")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.FINANCIAL_MANAGER.roleName()
                )))
                .build();
        userService.save(finAlice);

        // PRODUCTION
        User prodManager = new UserBuilder()
                .setName("Johan (Production manager)")
                .setUsername("prodm@sep.se")
                .setPassword("prodm")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.PRODUCTION.roleName(),
                        UserRole.TEAM_MANAGER.roleName()
                )))
                .build();
        userService.save(prodManager);
        User prodMember = new UserBuilder()
                .setName("Johan (Production member)")
                .setUsername("prod@sep.se")
                .setPassword("prod")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.PRODUCTION.roleName(),
                        UserRole.TEAM_MEMBER.roleName()
                )))
                .build();
        userService.save(prodMember);
        User prodJack = new UserBuilder()
                .setName("Jack")
                .setUsername("prodm-jack@sep.se")
                .setPassword("prodm")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.PRODUCTION.roleName(),
                        UserRole.TEAM_MANAGER.roleName()
                )))
                .build();
        userService.save(prodJack);
        User prodTobias = new UserBuilder()
                .setName("Tobias")
                .setUsername("prod-tobias@sep.se")
                .setPassword("prod")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.PRODUCTION.roleName(),
                        UserRole.TEAM_MEMBER.name()
                )))
                .build();
        userService.save(prodTobias);
        User prodJulia = new UserBuilder()
                .setName("Julia")
                .setUsername("prod-julia@sep.se")
                .setPassword("prod")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.PRODUCTION.roleName(),
                        UserRole.TEAM_MEMBER.name()
                )))
                .build();
        userService.save(prodJulia);
        User prodAdam = new UserBuilder()
                .setName("Adam")
                .setUsername("prod-adam@sep.se")
                .setPassword("prod")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.PRODUCTION.roleName(),
                        UserRole.TEAM_MEMBER.name()
                )))
                .build();
        userService.save(prodAdam);

        // SERVICES
        User servManager = new UserBuilder()
                .setName("Johan (Services manager)")
                .setUsername("servm@sep.se")
                .setPassword("servm")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.SERVICES.roleName(),
                        UserRole.TEAM_MANAGER.roleName()
                )))
                .build();
        userService.save(servManager);
        User servMember = new UserBuilder()
                .setName("Johan (Services member)")
                .setUsername("serv@sep.se")
                .setPassword("serv")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.SERVICES.roleName(),
                        UserRole.TEAM_MEMBER.roleName()
                )))
                .build();
        userService.save(servMember);
        User servNatalie = new UserBuilder()
                .setName("Natalie")
                .setUsername("servm-natalie@sep.se")
                .setPassword("servm")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.SERVICES.roleName(),
                        UserRole.TEAM_MEMBER.roleName()
                )))
                .build();
        userService.save(servNatalie);
        User servHelen = new UserBuilder()
                .setName("Helen")
                .setUsername("serv-helen@sep.se")
                .setPassword("serv")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.SERVICES.roleName(),
                        UserRole.TEAM_MEMBER.roleName()
                )))
                .build();
        userService.save(servHelen);
        User servKate = new UserBuilder()
                .setName("Kate")
                .setUsername("serv-kate@sep.se")
                .setPassword("serv")
                .setRoles(roleRepository.findByNames(Arrays.asList(
                        UserRole.SERVICES.roleName(),
                        UserRole.TEAM_MEMBER.name()
                )))
                .build();
        userService.save(servKate);


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
