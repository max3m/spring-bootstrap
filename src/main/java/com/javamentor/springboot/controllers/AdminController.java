package com.javamentor.springboot.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.javamentor.springboot.dao.RoleDAO;
import com.javamentor.springboot.model.User;
import com.javamentor.springboot.service.UserService;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleDAO roleDAO;
    private final PasswordEncoder bCryptPasswordEncoder;

    public AdminController(UserService userService, RoleDAO roleDAO, PasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.roleDAO = roleDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public String allUsers(Model model) {
        model.addAttribute("allUsersList", userService.allUsers());
        return "admin";
    }

    @GetMapping(value = "/new")
    public ModelAndView newUserPage(/*@ModelAttribute("user") User user, Model model*/) {
        /*model.addAttribute("rolesList", roleDAO.getRoleSet());
        return "new";*/
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("rolesList", roleDAO.getRoleSet());
        return modelAndView;
    }

    /*@PostMapping(value = "")
    public String newUserPost(@ModelAttribute("user") User user, HttpServletRequest req) {
        String[] selectedRoles = req.getParameterValues("roles");
        userService.save(user, selectedRoles);
        return "redirect:/admin";
    }*/
    @Transactional
    @PostMapping(value = "")
    public String newUserPost(@ModelAttribute("user") User user,
                              @RequestParam("selectedRoles") String[] selectedRoles) {
        userService.save(user, selectedRoles);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("rolesList", roleDAO.getRoleSet());
        return "edit";
    }

    @Transactional
    @PatchMapping(value = "/{id}")
    public String editUserPatch(@ModelAttribute("user") User user, HttpServletRequest req) {
        String[] selectedRoles = req.getParameterValues("selectedRoles");
        userService.update(user, selectedRoles);
        return "redirect:/admin";
    }

    @Transactional
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(userService.getById(id));
        return "redirect:/admin";
    }
}