package com.sep.controllers;

import com.sep.domain.FinanceRequest;
import com.sep.domain.FinanceRequestStatus;
import com.sep.repositories.FinanceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/finance")
public class FinanceController {
    @Autowired
    FinanceRequestRepository financeRequestRepository;

    @RequestMapping(value={"/request/new", "/request/create"})
    public String newFinanceRequest(Model model, @ModelAttribute("request") FinanceRequest req){
        return "financial/budget-negotiation-form";
    }
    @RequestMapping(value="/request", method = RequestMethod.POST)
    public String saveFinanceRequest(@Valid @ModelAttribute("request") FinanceRequest req,
                                      Errors errors,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Invalid input, please try again.");
            redirectAttributes.addFlashAttribute("request", req);
            return "redirect:/finance/request/create";
        }
        redirectAttributes.addFlashAttribute("currentId", req.getId());
        financeRequestRepository.save(req);
        return "redirect:/finance/request/list";
    }
    @RequestMapping(value = {"/request/list"}, method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("requests", financeRequestRepository.findAll());
        return "financial/list";

    }
    @GetMapping("/request/start/{id}")
    public String start(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        FinanceRequest it = financeRequestRepository.findOne(id);
        if (it == null) {
            redirectAttributes.addAttribute("error", "Invalid action! Request not found");
            return "redirect:/finance/request/list";
        }
        if (it.getStatus() != FinanceRequestStatus.OPEN) {
            redirectAttributes.addAttribute("error", "Invalid action! Request already started");
            return "redirect:/finance/request/list";
        }
        it.setStatus(FinanceRequestStatus.PENDING);
        financeRequestRepository.save(it);
        return "redirect:/finance/request/list";
    }
    @GetMapping("/request/close/{id}")
    public String close(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        FinanceRequest it = financeRequestRepository.findOne(id);
        if (it == null) {
            redirectAttributes.addAttribute("error", "Invalid action! Request not found");
            return "redirect:/finance/request/list";
        }
        if (it.getStatus() != FinanceRequestStatus.OPEN && it.getStatus() != FinanceRequestStatus.PENDING) {
            redirectAttributes.addAttribute("error", "Invalid action! Request already started");
            return "redirect:/finance/request/list";
        }
        it.setStatus(FinanceRequestStatus.CLOSED);
        financeRequestRepository.save(it);
        return "redirect:/finance/request/list";
    }
    @GetMapping("/request/reject/{id}")
    public String reject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        FinanceRequest it = financeRequestRepository.findOne(id);
        if (it == null) {
            redirectAttributes.addAttribute("error", "Invalid action! Request not found");
            return "redirect:/finance/request/list";
        }
        if (it.getStatus() != FinanceRequestStatus.OPEN && it.getStatus() != FinanceRequestStatus.PENDING) {
            redirectAttributes.addAttribute("error", "Invalid action! Request already started");
            return "redirect:/finance/request/list";
        }
        it.setStatus(FinanceRequestStatus.REJECTED);
        financeRequestRepository.save(it);
        return "redirect:/finance/request/list";
    }
}
