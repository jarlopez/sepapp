package com.sep.controllers;

import com.sep.common.UserRole;
import com.sep.domain.EPRStatus;
import com.sep.domain.EventPlanningRequest;
import com.sep.domain.TeamTask;
import com.sep.domain.User;
import com.sep.repositories.RoleRepository;
import com.sep.repositories.TeamTaskRepository;
import com.sep.repositories.UserRepository;
import com.sep.services.EventPlanningRequestService;
import com.sep.services.UserService;
import com.sep.util.SpringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/task")
public class TaskController {
    Logger log = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private EventPlanningRequestService eprService;
    @Autowired
    private TeamTaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SpringUtility util;

//    @Bean
    // TODO Beanify?
    private User currentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username);
    }

    @RequestMapping(value = {"/mgmt/listAll"}, method = RequestMethod.GET)
    @PreAuthorize("hasRole('TEAM_MANAGER')")
    public String listAll(Model model){
        model.addAttribute("tasks", taskRepository.findAll());
        return "task/list";
    }

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    @PreAuthorize("hasRole('TEAM_MEMBER')")
    public String list(Model model){
        User me = currentUser();
        Set<TeamTask> own = taskRepository.findByAssignedTo(me);
        model.addAttribute("tasks", own);
        return "task/list";
    }

    @RequestMapping(value={"/mgmt/new", "/mgmt/create"})
    public String newEventPlanningRequest(Model model, @ModelAttribute("task") TeamTask teamTask){
        // Determine valid team candidates
        Set<String> rolesToCheck = new HashSet<>();
        rolesToCheck.add(UserRole.TEAM_MEMBER.roleName());
        String team;
        if (util.hasRole(UserRole.PRODUCTION.roleName())) {
            rolesToCheck.add(UserRole.PRODUCTION.roleName());
            team = "Production Team";
        } else {
            rolesToCheck.add(UserRole.SERVICES.roleName());
            team = "Services Team";
        }
        Set<User> candidates = userService.findByRoleNames(rolesToCheck);
        model.addAttribute("team", team);
        model.addAttribute("candidates", candidates);

        // Extract valid EPRs
        List<EventPlanningRequest> eprs = eprService.getEventPlanningRequestsByStatus(EPRStatus.APPROVED);
        model.addAttribute("eprs", eprs);

        return "task/form";
    }
}
