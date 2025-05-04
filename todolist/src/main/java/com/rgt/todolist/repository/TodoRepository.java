package com.rgt.todolist.repository;

import com.rgt.todolist.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Todo> findByUserIdAndCompleted(Long userId, boolean completed);
    List<Todo> findByDueDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Todo> findByDueDateBeforeAndCompletedFalse(LocalDateTime dueDate);
    List<Todo> findByDueDateAfter(LocalDateTime dueDate);
}
