package com.sep.controllers;

import com.sep.repositories.ClientRepository;
import com.sep.repositories.StaffRequestRepository;
import com.sep.repositories.UserRepository;
import com.sep.services.AuditService;
import com.sep.services.EventPlanningRequestService;
import com.sep.services.StaffRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Default Cute on 17-10-2016.
 */
@Controller
@RequestMapping("/staff")
public class StaffRequestController {
    Logger log = LoggerFactory.getLogger(StaffRequestController.class);

    @Autowired
    private EventPlanningRequestService eprService;
    @Autowired
    private AuditService auditService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StaffRequestRepository staffRequestRepository;
    @Autowired
    private StaffRequestService staffService;

    //@RequestMapping("/form")
    //public String edit(@PathVariable Long id, Model model) {
     //   model.addAttribute("epr", staffRequestRepository.findOne(id));
      //  return "staff/form";
    }
