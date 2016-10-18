package integration;


import com.sep.SepApplication;
import com.sep.bootstrap.UserBuilder;
import com.sep.domain.AuditRecord;
import com.sep.domain.EventPlanningRequest;
import com.sep.domain.Role;
import com.sep.repositories.EventPlanningRequestRepository;
import com.sep.repositories.RoleRepository;
import com.sep.services.AuditService;
import com.sep.services.EventPlanningRequestService;
import com.sep.services.SecurityService;
import com.sep.services.UserService;
import config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@ContextConfiguration(classes = {SepApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTests {
    Logger log = LoggerFactory.getLogger(ServiceTests.class);


    @Autowired
    SecurityService securityService;
    @Autowired
    AuditService auditService;
    @Autowired
    UserService userService;
    @Autowired
    EventPlanningRequestService eprService;
    @Autowired
    EventPlanningRequestRepository eprRepository;
    @Autowired
    RoleRepository roleRepository;

    @Test
    public void testSecurityServiceAutoLogin() throws Exception {
        securityService.autologin(TestConfig.SUDO_UN, TestConfig.SUDO_PW);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assert(TestConfig.SUDO_UN.equals(user.getUsername()));
    }

    @Test
    @Transactional
    public void testAuditServiceLookupAuditHistory() throws Exception {
        // Setup, tested under unit.domain.DomainTest
        EventPlanningRequest epr = new EventPlanningRequest();
        eprRepository.save(epr);
        Long id = epr.getId();

        // Base case, no audits
        Iterable<AuditRecord> initialAudits = auditService.getHistoryForRepoAndId(eprRepository, id);
        assert(!initialAudits.iterator().hasNext());

        // Append an audit
        AuditRecord ar = new AuditRecord();
        ar.setField("TEST-FIELD");
        ar.setNewValue("TEST-NEW-VALUE");
        auditService.saveAuditRecord(ar);
        epr.addToAuditHistory(ar);
        eprRepository.save(epr);
        log.info("Checking audits");
        // Ensure non-empty collection
        Iterable<AuditRecord> finalAudits = auditService.getHistoryForRepoAndId(eprRepository, id);
        assert(finalAudits.iterator().hasNext());
    }

    @Test
    @Transactional
    public void testUserLookupByRoles() throws Exception {
        // Set up
        Role newRole = new Role();
        newRole.setName("ROLE_TEST");
        roleRepository.save(newRole);
        Long roleId = newRole.getId();

        com.sep.domain.User testUser = new UserBuilder()
                .addRole(newRole)
                .setPassword("test")
                .setUsername("test@test.se")
                .setName("test user")
                .build();
        userService.save(testUser);

        // Verify lookup
        Set<com.sep.domain.User> check = userService.findByRoleNames(new HashSet<String>() {{
            add("ROLE_TEST");
        }});
        log.info("Looked up: " + check);
        assert(check.size() != 0);
        assert(check.contains(testUser));
    }
}
