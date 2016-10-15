package com.sep.bootstrap;

import com.sep.domain.EPRStatus;
import com.sep.domain.EventPlanningRequest;
import com.sep.repositories.ClientRepository;
import com.sep.repositories.EventPlanningRequestRepository;
import com.sep.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class EventPlanningRequestLoader implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    @Autowired
    private EventPlanningRequestRepository eprRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;

    private Logger log = Logger.getLogger(EventPlanningRequestLoader .class);

    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {

        EventPlanningRequest holidayParty = new EventPlanningRequest();
        holidayParty.setName("Holiday Party");
        holidayParty.setDescription("Christmas party with food");
        holidayParty.setClient(clientRepository.findByName("KTH"));
        holidayParty.setCreator(userRepository.findByUsername("cs-sam@sep.se"));
        holidayParty.setStatus(EPRStatus.CLOSED);

        eprRepository.save(holidayParty);

        EventPlanningRequest schoolOuting = new EventPlanningRequest();
        schoolOuting.setName("School Outing");
        schoolOuting.setDescription("Trip to the zoo");
        schoolOuting.setClient(clientRepository.findByName("KTH"));
        schoolOuting.setCreator(userRepository.findByUsername("cs-judy@sep.se"));

        eprRepository.save(schoolOuting);
    }
}
