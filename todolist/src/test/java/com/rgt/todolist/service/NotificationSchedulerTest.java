package com.rgt.todolist.service;

import com.rgt.todolist.model.Todo;
import com.rgt.todolist.model.User;
import com.rgt.todolist.repository.TodoRepository;
import com.rgt.todolist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
@Rollback
class NotificationSchedulerIntegrationTest {

    @Autowired
    private NotificationScheduler notificationScheduler;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        // Create and save a test user
        User user = new User();
        user.setEmail("rgtwork114@gmail.com");
        user.setFullName("Test User");
        user.setPassword("1234");
        userRepository.save(user);

        // Create and save test tasks
        Todo todo1 = new Todo();
        todo1.setTitle("Test Task 1");
        todo1.setDueDate(LocalDateTime.now().plusDays(1)); // Future task
        todo1.setCompleted(false);
        todo1.setUser(user);
        todoRepository.save(todo1);

        Todo todo2 = new Todo();
        todo2.setTitle("Test Task 2");
        todo2.setDueDate(LocalDateTime.now().plusDays(2)); // Future task
        todo2.setCompleted(false);
        todo2.setUser(user);
        todoRepository.save(todo2);

        Todo todo3 = new Todo();
        todo3.setTitle("Completed Task");
        todo3.setDueDate(LocalDateTime.now().minusDays(1)); // Past task
        todo3.setCompleted(true);
        todo3.setUser(user);
        todoRepository.save(todo3);
    }

    @Test
    void testSendPeriodicEmailWithDbData() {
        // Act
        notificationScheduler.sendPeriodicEmail();

        // Assert
        List<Todo> futureTodos = todoRepository.findByDueDateAfter(LocalDateTime.now());
        assertThat(futureTodos).hasSize(2);

        // Verify the email service is called for the correct tasks
        for (Todo todo : futureTodos) {
            verify(emailService).sendEmail(
                    todo.getUser().getEmail(),
                    "Upcoming Task Reminder",
                    "Your task \"" + todo.getTitle() + "\" is due on " + todo.getDueDate() + ". Don't forget to complete it!"
            );
        }
    }
}