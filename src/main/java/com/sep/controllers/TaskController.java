package com.sep.controllers;

import com.sep.common.UserRole;
import com.sep.domain.*;
import com.sep.repositories.TeamTaskRepository;
import com.sep.repositories.UserRepository;
import com.sep.services.AuditService;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
    private AuditService auditService;

    @Autowired
    private SpringUtility util;

    // TODO Beanify?
    private User currentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username);
    }

    @GetMapping("/{id}")
    public String viewTeamTask(Model model, @PathVariable Long id) {
        TeamTask task = taskRepository.findOne(id);
        if (task == null) {
            model.addAttribute("error", "Task could not be found");
            return "error";
        }
        model.addAttribute("task", task);
        return "task/view";
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

    @RequestMapping(value="", method = RequestMethod.POST)
    public String saveTeamTask(@Valid @ModelAttribute("task") TeamTask task,
                                      Errors errors,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            log.warn("Incoming task has errors: " + errors.getAllErrors());
            redirectAttributes.addFlashAttribute("error", "Invalid input, please try again.");
            redirectAttributes.addFlashAttribute("request", task);
            return "redirect:/task/mgmt/create";
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        task.setSender(user);
        AuditRecord ar = new AuditRecord();
        ar.setField("Status");
        ar.setNewValue("Created by team manager");
        ar.setModifiedBy(user);
        auditService.saveAuditRecord(ar);
        task.addToAuditHistory(ar);

        taskRepository.save(task);
        redirectAttributes.addFlashAttribute("currentId", task.getId());
        redirectAttributes.addFlashAttribute("success",  "Successfully created a new staffing request");
        return "redirect:/task/mgmt/listAll";
    }


    @RequestMapping("/feedback/{id}")
    public String financialFeedback(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        TeamTask task = taskRepository.findOne(id);
        if (task.getStatus() != TaskStatus.OPEN) {
            redirectAttributes.addFlashAttribute("error", "Improper flow");
            redirectAttributes.addFlashAttribute("message", "Feedback cannot be provided for the request.");
            return "redirect:/epr/list";
        }
        model.addAttribute("task", task);
        return "task/feedback";
    }
    @RequestMapping(value = "/feedback/{id}", method = RequestMethod.POST)
    public String saveFinancialFeedback(@RequestParam String feedback, @PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        TeamTask task = taskRepository.findOne(id);

        // Audit
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        AuditRecord ar = new AuditRecord();
        ar.setField("Status");
        ar.setNewValue("Feedback provided by team member");
        ar.setModifiedBy(user);
        auditService.saveAuditRecord(ar);
        task.addToAuditHistory(ar);

        task.setFeedback(feedback);
        task.setStatus(TaskStatus.FEEDBACK_SUBMITTED);
        taskRepository.save(task);
        redirectAttributes.addFlashAttribute("info", "Feedback submitted");
        return "redirect:/task/list";
    }

    @RequestMapping("/start/{id}")
    public String stastTask(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        TeamTask task = taskRepository.findOne(id);
        if (task.getStatus() != TaskStatus.FEEDBACK_SUBMITTED) {
            redirectAttributes.addFlashAttribute("error", "Improper flow");
            redirectAttributes.addFlashAttribute("message", "Progress has already been started on this task");
            return "redirect:/epr/list";
        }
        // Audit
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        AuditRecord ar = new AuditRecord();
        ar.setField("Status");
        ar.setNewValue("Team member started progress");
        ar.setModifiedBy(user);
        auditService.saveAuditRecord(ar);
        task.addToAuditHistory(ar);

        task.setStatus(TaskStatus.IN_PROGRESS);
        taskRepository.save(task);

        redirectAttributes.addFlashAttribute("info", "Started progress on task");
        return "redirect:/task/list";
    }
    @RequestMapping("/finish/{id}")
    public String finishTask(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        TeamTask task = taskRepository.findOne(id);
        if (task.getStatus() != TaskStatus.IN_PROGRESS) {
            redirectAttributes.addFlashAttribute("error", "Improper flow");
            redirectAttributes.addFlashAttribute("message", "Cannot stop work on a task not in progress");
            return "redirect:/epr/list";
        }
        // Audit
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        AuditRecord ar = new AuditRecord();
        ar.setField("Status");
        ar.setNewValue("Team member stopped progress");
        ar.setModifiedBy(user);
        auditService.saveAuditRecord(ar);
        task.addToAuditHistory(ar);

        task.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(task);

        redirectAttributes.addFlashAttribute("info", "Completed work on task");
        return "redirect:/task/list";
    }

    @RequestMapping("/verify/{id}")
    public String verifyTask(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        TeamTask task = taskRepository.findOne(id);
        if (task.getStatus() != TaskStatus.COMPLETED) {
            redirectAttributes.addFlashAttribute("error", "Improper flow");
            redirectAttributes.addFlashAttribute("message", " verify incomplete task");
            return "redirect:/epr/list";
        }
        // Audit
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        AuditRecord ar = new AuditRecord();
        ar.setField("Status");
        ar.setNewValue("Team manager verified task outcome");
        ar.setModifiedBy(user);
        auditService.saveAuditRecord(ar);
        task.addToAuditHistory(ar);

        task.setStatus(TaskStatus.VERIFIED);
        taskRepository.save(task);

        redirectAttributes.addFlashAttribute("info", "Verified work done in task");
        return "redirect:/task/list";
    }


}
