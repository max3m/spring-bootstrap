package com.javamentor.springboot.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.javamentor.springboot.dao.RoleDAO;
import com.javamentor.springboot.model.User;
import com.javamentor.springboot.service.UserService;

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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("authorizedUser", user);
        model.addAttribute("userRoles", userService.showRoles(user));
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleDAO.getRoleSet());
        return "admin";
    }

    @PostMapping("/create")
    public String addUser(@ModelAttribute("user") User user, @RequestParam(value = "roleList") String [] selectedRoles){
        userService.save(user, selectedRoles);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String editUser(@ModelAttribute User user ,@RequestParam(value = "roleList") String [] selectedRoles ){
        userService.save(user, selectedRoles);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(userService.getById(id));
        return "redirect:/admin";
    }
}