package com.sep.controllers;

import com.sep.domain.StaffingRequest;
import com.sep.domain.StaffingRequestStatus;
import com.sep.repositories.StaffingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    StaffingRequestRepository staffingRequestRepository;

    @RequestMapping(value={"/request/new", "/request/create"})
    public String newStaffingRequest(Model model, @ModelAttribute("request") StaffingRequest req){
        return "staffing/recruitment-request-form";
    }

    @RequestMapping(value="/request", method = RequestMethod.POST)
    public String saveStaffingRequest(@Valid @ModelAttribute("request") StaffingRequest req,
                                           Errors errors,
                                           Model model,
                                           RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Invalid input, please try again.");
            redirectAttributes.addFlashAttribute("request", req);
            return "redirect:/staff/request/create";
        }
        redirectAttributes.addFlashAttribute("currentId", req.getId());
        staffingRequestRepository.save(req);
        return "redirect:/staff/request/list";
    }

    @RequestMapping(value = {"/request/list"}, method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("requests", staffingRequestRepository.findAll());
        return "staffing/list";
    }

    @GetMapping("/request/start/{id}")
    public String start(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        StaffingRequest it = staffingRequestRepository.findOne(id);
        if (it == null) {
            redirectAttributes.addAttribute("error", "Invalid action! Request not found");
            return "redirect:/staff/request/list";
        }
        if (it.getStatus() != StaffingRequestStatus.OPEN) {
            redirectAttributes.addAttribute("error", "Invalid action! Request already started");
            return "redirect:/staff/request/list";
        }
        it.setStatus(StaffingRequestStatus.PENDING);
        staffingRequestRepository.save(it);
        return "redirect:/staff/request/list";
    }
    @GetMapping("/request/close/{id}")
    public String close(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        StaffingRequest it = staffingRequestRepository.findOne(id);
        if (it == null) {
            redirectAttributes.addAttribute("error", "Invalid action! Request not found");
            return "redirect:/staff/request/list";
        }
        if (it.getStatus() != StaffingRequestStatus.OPEN && it.getStatus() != StaffingRequestStatus.PENDING) {
            redirectAttributes.addAttribute("error", "Invalid action! Request already started");
            return "redirect:/staff/request/list";
        }
        it.setStatus(StaffingRequestStatus.CLOSED);
        staffingRequestRepository.save(it);
        return "redirect:/staff/request/list";
    }
    @GetMapping("/request/reject/{id}")
    public String reject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        StaffingRequest it = staffingRequestRepository.findOne(id);
        if (it == null) {
            redirectAttributes.addAttribute("error", "Invalid action! Request not found");
            return "redirect:/staff/request/list";
        }
        if (it.getStatus() != StaffingRequestStatus.OPEN && it.getStatus() != StaffingRequestStatus.PENDING) {
            redirectAttributes.addAttribute("error", "Invalid action! Request already started");
            return "redirect:/staff/request/list";
        }
        it.setStatus(StaffingRequestStatus.REJECTED);
        staffingRequestRepository.save(it);
        return "redirect:/staff/request/list";
    }
}


