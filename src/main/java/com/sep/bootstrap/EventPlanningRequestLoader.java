package com.sep.bootstrap;

import com.sep.domain.EventPlanningRequest;
import com.sep.repositories.EventPlanningRequestRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class EventPlanningRequestLoader implements ApplicationListener<ContextRefreshedEvent> {

    private EventPlanningRequestRepository eprRepository;

    private Logger log = Logger.getLogger(EventPlanningRequestLoader .class);

    @Autowired
    public void setProductRepository(EventPlanningRequestRepository eprRepository) {
        this.eprRepository = eprRepository;
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {

        EventPlanningRequest holidayParty = new EventPlanningRequest();
        holidayParty.setDescription("Holiday Party");

        eprRepository.save(holidayParty);

        EventPlanningRequest schoolOuting = new EventPlanningRequest();
        schoolOuting.setDescription("School Outing");

        eprRepository.save(schoolOuting);
    }
}
