package com.rgt.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodoController {

    @GetMapping("/")
    public String index() {
        return "index"; // This will return src/main/resources/templates/index.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // This will return src/main/resources/templates/login.html
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup"; // This will return src/main/resources/templates/signup.html
    }
}