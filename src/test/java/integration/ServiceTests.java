package integration;


import com.sep.SepApplication;
import com.sep.domain.AuditRecord;
import com.sep.domain.EventPlanningRequest;
import com.sep.repositories.EventPlanningRequestRepository;
import com.sep.services.AuditService;
import com.sep.services.EventPlanningRequestService;
import com.sep.services.SecurityService;
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

@ContextConfiguration(classes = {SepApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTests {
    Logger log = LoggerFactory.getLogger(ServiceTests.class);


    @Autowired
    SecurityService securityService;
    @Autowired
    AuditService auditService;
    @Autowired
    EventPlanningRequestService eprService;
    @Autowired
    EventPlanningRequestRepository eprRepository;

    @Test
    public void testSecurityServiceAutoLogin() throws Exception {
        securityService.autologin("test@sep.se", "test");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assert("test@sep.se".equals(user.getUsername()));
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
        assert(initialAudits.iterator().hasNext());

    }
}
