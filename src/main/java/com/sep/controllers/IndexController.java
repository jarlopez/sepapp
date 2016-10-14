package com.sep.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @RequestMapping("/")
    String index(){
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "welcome";
    }

    @GetMapping("/error")
    public String error(Model model) {
        return "error";
    }
}
