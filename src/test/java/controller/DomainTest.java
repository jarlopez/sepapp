package controller;

import com.sep.SepApplication;
import com.sep.domain.AuditRecord;
import com.sep.domain.Client;
import com.sep.domain.EventPlanningRequest;
import com.sep.repositories.AuditRepository;
import com.sep.repositories.ClientRepository;
import com.sep.repositories.EventPlanningRequestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = {SepApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DomainTest {
    Logger log = LoggerFactory.getLogger(DomainTest.class);


    @Autowired
    EventPlanningRequestRepository eprRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AuditRepository auditRepository;

    @Test
    public void testCreateClients() throws Exception {
        Integer initialSize = clientRepository.findAll().size();
        Client kth = new Client();
        kth.setName("TEST-CLIENT");
        clientRepository.save(kth);
        assert(clientRepository.findAll().size() == (initialSize + 1));
        assert(clientRepository.findByName("TEST-CLIENT") != null);
    }

    @Test
    public void testCreateEpr() throws Exception {
        Integer initialSize = eprRepository.findAll().size();
        EventPlanningRequest epr = new EventPlanningRequest();
        eprRepository.save(epr);
        Long id = epr.getId();
        assert(eprRepository.findAll().size() == (initialSize + 1));
        assert(eprRepository.findOne(id) != null);
    }

    @Test
    public void testAppendAudits() throws Exception {
        EventPlanningRequest epr = eprRepository.findAll().get(0);
        Long id = epr.getId();
        log.info(epr.toString());
        AuditRecord newAudit = new AuditRecord();
        newAudit.setField("Test Field");
        newAudit.setNewValue("Some edited value");
        auditRepository.save(newAudit);
        epr.addToAuditHistory(newAudit);
        eprRepository.save(epr);
        assert(eprRepository.findOne(id).getAuditHistory().size() == 1);
    }


}