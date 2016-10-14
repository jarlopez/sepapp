package com.sep.bootstrap;

import com.sep.domain.Client;
import com.sep.repositories.ClientRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ClientLoader implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ClientRepository clientRepository;

    private Logger log = Logger.getLogger(EventPlanningRequestLoader .class);


    public void onApplicationEvent(ContextRefreshedEvent event) {
        Client kth = new Client();
        kth.setName("KTH");
        clientRepository.save(kth);
//
//        EventPlanningRequest holidayParty = new EventPlanningRequest();
//        holidayParty.setDescription("Spring Framework Guru Shirt");
//        holidayParty.setPrice(new BigDecimal("18.95"));
//        holidayParty.setImageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_holidayParty-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg");
//        holidayParty.setEprId("235268845711068308");
//        eprRepository.save(holidayParty);
//
//        log.info("Saved Shirt - id: " + holidayParty.getId());
//
//        EventPlanningRequest schoolOuting = new EventPlanningRequest();
//        schoolOuting.setDescription("Spring Framework Guru Mug");
//        schoolOuting.setImageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_coffee_schoolOuting-r11e7694903c348e1a667dfd2f1474d95_x7j54_8byvr_512.jpg");
//        schoolOuting.setEprId("168639393495335947");
//        schoolOuting.setPrice(new BigDecimal("11.95"));
//        eprRepository.save(schoolOuting);
//
//        log.info("Saved Mug - id:" + schoolOuting.getId());
    }
}
