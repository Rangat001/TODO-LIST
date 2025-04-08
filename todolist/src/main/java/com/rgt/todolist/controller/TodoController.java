package com.rgt.todolist.controller;

import com.rgt.todolist.model.Todo;
import com.rgt.todolist.model.User;
import com.rgt.todolist.repository.UserRepository;
import com.rgt.todolist.service.TodoService;
import com.rgt.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    private UserService userService;

    @Autowired
    private TodoService todoService;

    @PostMapping("/create")
    public ResponseEntity<?> create_todo(@RequestBody Todo todo){
        if(todo.getTitle() != null && todo.getDueDate() != null && todo.getDescription() != null){
            todoService.createTodo(todo);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(/"edit")
    public  ResponseEntity<?> edit_todo(@RequestBody Todo todo){
        
    }

}