package com.sep.controllers;

import com.sep.domain.Client;
import com.sep.repositories.ClientRepository;
import com.sep.repositories.UserRepository;
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
@RequestMapping("/client")
public class ClientController {
    Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    // TODO Wrap with service
    private ClientRepository clientRepository;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "client/list";
    }

    @RequestMapping("/{id}")
    public String showClientDetails(@PathVariable Long id, Model model){
        model.addAttribute("client", clientRepository.findOne(id));
        return "client/view";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("epr", clientRepository.findOne(id));
        return "client/form";
    }
    @RequestMapping(value={"/new", "/create"})
    public String newClient(Model model, @ModelAttribute("client") Client client){
        return "client/form";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveClientDetails(@Valid @ModelAttribute("client") Client client,
                                           Errors errors,
                                           Model model,
                                           RedirectAttributes redirectAttributes){
        if (errors.hasErrors()) {
            log.info(errors.getAllErrors().toString());
            redirectAttributes.addFlashAttribute("error", "Invalid input, please try again.");
            redirectAttributes.addFlashAttribute("client", client);
            return "redirect:/client/create";
        }
        try {
            clientRepository.save(client);
            redirectAttributes.addFlashAttribute("info", "New client successfully created.");
            return "redirect:/client/" + client.getId();
        } catch(Exception ex) {
            model.addAttribute("client", client);
            model.addAttribute("error", "Invalid input, please try again.");
            return "redirect:/client/create";
        }
    }
}
