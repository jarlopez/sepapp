package com.sep.controllers;

import com.sep.domain.FinanceRequest;
import com.sep.repositories.FinanceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by Default Cute on 17-10-2016.
 */
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
}
