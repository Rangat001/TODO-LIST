package com.rgt.todolist.controller;

import com.rgt.todolist.model.Todo;
import com.rgt.todolist.model.User;

import com.rgt.todolist.service.TodoService;
import com.rgt.todolist.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            todoService.createTodo(todo,email);
            return new ResponseEntity<>(todo,HttpStatus.CREATED);
        }
        return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

        @PutMapping("/edit/{id}")
        public  ResponseEntity<?> edit_todo(@RequestBody Todo todo,@PathVariable Long id){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username =  authentication.getName();
            if(username != null){
                todoService.updateTodo(todo,id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllTodo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  authentication.getName();
        if(username!=null && !username.isEmpty() ){
            User user =  userService.findByEmail(username);
            return new ResponseEntity<>(todoService.getUserTodos(user.getId()),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletebyid(@PathVariable Long id){
        Todo temp = todoService.findbyid(id);
        if (temp == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}