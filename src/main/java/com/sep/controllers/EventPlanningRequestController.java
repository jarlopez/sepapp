package com.sep.controllers;

import com.sep.domain.EventPlanningRequest;
import com.sep.repositories.ClientRepository;
import com.sep.services.EventPlanningRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/epr")
public class EventPlanningRequestController {
    Logger log = LoggerFactory.getLogger(EventPlanningRequestController.class);

    @Autowired
    private EventPlanningRequestService eprService;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("eprs", eprService.listAllEventPlanningRequests());
        return "epr/list";
    }

    @RequestMapping("/{id}")
    public String showEventPlanningRequest(@PathVariable Long id, Model model){
        EventPlanningRequest epr = eprService.getEventPlanningRequestById(id);
        if (epr != null) {
            log.info("Yo yo, it exists: " + epr.getId());
        }
        model.addAttribute("epr", eprService.getEventPlanningRequestById(id));
        return "epr/view";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("epr", eprService.getEventPlanningRequestById(id));
        return "epr/form";
    }

    @RequestMapping(value={"/new", "/create"})
    public String newEventPlanningRequest(Model model, @ModelAttribute("epr") EventPlanningRequest epr){
        model.addAttribute("clients", clientRepository.findAll());
        return "epr/form";
    }

    /*
    public String post(@Valid @ModelAttribute("myForm") MyForm myForm, Errors errors, Model model) {
    if (errors.hasErrors()) {
        MyForm emptyForm = new MyForm();
        BeanUtils.copyProperties(emptyForm, myForm);
        return "my_form";
    }
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveEventPlanningRequest(@Valid @ModelAttribute("epr") EventPlanningRequest eventPlanningRequest,
                                           Errors errors,
                                           Model model,
                                           RedirectAttributes redirectAttributes){
        if (errors.hasErrors()) {
            log.info(errors.getAllErrors().toString());
            redirectAttributes.addFlashAttribute("error", "Invalid input, please try again.");
            redirectAttributes.addFlashAttribute("epr", eventPlanningRequest);
            return "redirect:/epr/create";
        }
        try {
            eprService.saveEventPlanningRequest(eventPlanningRequest);
            return "redirect:/epr/" + eventPlanningRequest.getId();
        } catch(Exception ex) {
            model.addAttribute("epr", eventPlanningRequest);
            model.addAttribute("error", "Invalid input, please try again.");
            model.addAttribute("message", "Invalid input, please try again.");

            return "redirect:/epr/create";
        }
    }

}
