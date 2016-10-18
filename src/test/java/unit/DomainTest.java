package unit;

import com.sep.SepApplication;
import com.sep.domain.*;
import com.sep.repositories.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Autowired
    TeamTaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StaffingRequestRepository staffRepository;
    @Autowired
    FinanceRequestRepository financeRepository;

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

    @Test
    public void testCreateTeamTask() throws Exception {
        // Setup -- get some users
        final String description = "Do some stuff!";
        List<User> all = userRepository.findAll();
        User sender = all.get(0);
        User assignee = all.get(1);

        // Create team task with assignee and sender
        TeamTask task = new TeamTask();
        EventPlanningRequest epr = eprRepository.findAll().get(0);
        task.setEpr(epr);
        task.setPriority(TaskPriority.HIGH);
        task.setDescription(description);
        task.setSender(sender);
        task.setAssignedTo(assignee);
        taskRepository.save(task);
        Long id = task.getId();

        TeamTask check = taskRepository.getOne(id);

        assert(check.getDescription().equals(description));
        assert(check.getPriority().equals(TaskPriority.HIGH));
        assert(check.getSender().getId().equals(sender.getId()));
        assert(check.getAssignedTo().getId().equals(assignee.getId()));
    }

    @Test
    public void testCreateStaffingRequest() throws Exception {
        // SETUP: Create new object, try to save
        StaffingRequest sr = new StaffingRequest();
        sr.setDescription("Test description");
        sr.setContractType(ContractType.FULL_TIME);
        sr.setDepartment(Department.ADMINISTRATION);
        sr.setJobTitle("SQA");

        staffRepository.save(sr);
        Long id = sr.getId();

        // Check retrieval
        StaffingRequest check = staffRepository.findOne(id);
        assert(check != null);
        assert(check.getContractType().equals(ContractType.FULL_TIME));
        assert(check.getId().equals(id));
        log.info("All checks passed");
    }

    @Test
    public void testCreateFinanceRequest() throws Exception {
        // SETUP: Create new object, try to save
        FinanceRequest fr = new FinanceRequest();
        fr.setRequestingDepartment(Department.ADMINISTRATION);
        fr.setProjectReference("ep456");
        fr.setRequiredAmount(1000);
        fr.setReason("Resources Required");

        financeRepository.save(fr);
        Long id = fr.getId();

        // Check retrieval
        FinanceRequest check = financeRepository.findOne(id);
        assert(check != null);
        assert(check.getRequestingDepartment().equals(Department.ADMINISTRATION));
        assert(check.getId().equals(id));
        log.info("All checks passed");
    }

}


