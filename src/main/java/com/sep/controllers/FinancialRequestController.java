package com.sep.controllers;

import com.sep.repositories.ClientRepository;
import com.sep.repositories.FinancialRequestRepository;
import com.sep.repositories.UserRepository;
import com.sep.services.AuditService;
import com.sep.services.EventPlanningRequestService;
import com.sep.services.FinancialRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Default Cute on 16-10-2016.
 */
@Controller
@RequestMapping("/fin")
public class FinancialRequestController {
    Logger log = LoggerFactory.getLogger(FinancialRequestController.class);

    @Autowired
    private EventPlanningRequestService eprService;
    @Autowired
    private AuditService auditService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FinancialRequestRepository financialRequestRepository;
    @Autowired
    private FinancialRequestService finService;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("fins", finService.listAllFinancialRequests());

        return "fin/list";
    }
}
