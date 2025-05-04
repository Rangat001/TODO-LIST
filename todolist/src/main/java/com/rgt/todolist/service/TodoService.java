package com.rgt.todolist.service;

import com.rgt.todolist.model.User;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;
import com.rgt.todolist.model.Todo;
import com.rgt.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private  UserService userService;

    public void createTodo(Todo todo,String email) {
        User user = userService.findByEmail(email);
        try{
            if (user != null){
                todo.setUser(user);
                todo.setCreatedAt(LocalDateTime.now());
                todoRepository.save(todo);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Todo> getUserTodos(Long userId) {
        return todoRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Todo updateTodo(Todo todo, Long id) {
        if (!todoRepository.existsById(id)){
            throw new RuntimeException("Todo not found");
        }

        Todo temp = todoRepository.getReferenceById(id);
        if(todo.getTitle() != null || todo.getTitle() != "" && todo.getDescription()!= null || todo.getDescription() != " "){
            temp.setTitle(todo.getTitle());
            temp.setDescription(todo.getDescription());
//            temp.setCreatedAt(todo.getCreatedAt());
            temp.setCompleted(todo.getcompleted());
            temp.setDueDate(todo.getDueDate());
        }
        return todoRepository.save(temp);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public Todo findbyid(Long id){
        return todoRepository.getById(id);
    }
}
