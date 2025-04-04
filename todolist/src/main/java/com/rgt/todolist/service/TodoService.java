package com.rgt.todolist.service;

import org.springframework.stereotype.Service;
import com.rgt.todolist.model.Todo;
import com.rgt.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public List<Todo> getUserTodos(Long userId) {
        return todoRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Todo updateTodo(Todo todo) {
        if (!todoRepository.existsById(todo.getId())) {
            throw new RuntimeException("Todo not found");
        }
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
