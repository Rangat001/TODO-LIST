package com.rgt.todolist.controller;

import com.rgt.todolist.model.User;
import com.rgt.todolist.repository.UserRepository;
import com.rgt.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodoController {
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            User user = userService.findByEmail(auth.getName());
            model.addAttribute("user", user);
        }
        return "index";
    }

}