package com.rgt.todolist.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rgt.todolist.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private boolean completed;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public boolean getcompleted(){
        return completed;
    }

    // Add fields for notification tracking
    private boolean weekNotificationSent = false;

    private boolean dayNotificationSent = false;

    // Getters and Setters
    public boolean isWeekNotificationSent() {
        return weekNotificationSent;
    }

    public void setWeekNotificationSent(boolean weekNotificationSent) {
        this.weekNotificationSent = weekNotificationSent;
    }

    public boolean isDayNotificationSent() {
        return dayNotificationSent;
    }

    public void setDayNotificationSent(boolean dayNotificationSent) {
        this.dayNotificationSent = dayNotificationSent;
    }
}