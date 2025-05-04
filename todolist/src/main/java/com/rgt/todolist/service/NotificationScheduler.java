package com.rgt.todolist.service;

import com.rgt.todolist.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationScheduler {

    @Autowired
    private TodoService todoService;

    @Autowired
    private EmailService emailService;

    // Run every day at midnight to check tasks due in 7 days
    @Scheduled(cron = "0 0 0 * * *")
    public void sendWeeklyNotifications() {
        List<Todo> todos = todoService.getTodosDueInDays(7);
        for (Todo todo : todos) {
            if (!todo.isWeekNotificationSent()) {
                emailService.sendEmail(todo.getUser().getEmail(),
                        "Reminder: Task due in 7 days",
                        "Your task \"" + todo.getTitle() + "\" is due on " + todo.getDueDate());
                todo.setWeekNotificationSent(true);
                todoService.save(todo); // Persist the updated notification status
            }
        }
    }

    // Run every day at 6 AM, 12 PM, and 6 PM to check tasks due in 1 day
    @Scheduled(cron = "0 0 6,12,18 * * *")
    public void sendDailyNotifications() {
        List<Todo> todos = todoService.getTodosDueInDays(1);
        for (Todo todo : todos) {
            if (!todo.isDayNotificationSent()) {
                emailService.sendEmail(todo.getUser().getEmail(),
                        "Reminder: Task due in 1 day",
                        "Your task \"" + todo.getTitle() + "\" is due tomorrow at " + todo.getDueDate());
                todo.setDayNotificationSent(true);
                todoService.save(todo); // Persist the updated notification status
            }
        }
    }

    // Run every 2 hours to check tasks due in less than a day
    @Scheduled(cron = "0 0 */2 * * *")
    public void sendHourlyNotifications() {
        List<Todo> todos = todoService.getTodosDueInHours(24);
        for (Todo todo : todos) {
            emailService.sendEmail(todo.getUser().getEmail(),
                    "Urgent: Task due soon",
                    "Your task \"" + todo.getTitle() + "\" is due in less than 24 hours on " + todo.getDueDate());
        }
    }

    // Run every 30 minutes to check tasks due in the next 30 minutes
    @Scheduled(cron = "0 */30 * * * *")
    public void sendFinalNotifications() {
        List<Todo> todos = todoService.getTodosDueInMinutes(30);
        for (Todo todo : todos) {
            emailService.sendEmail(todo.getUser().getEmail(),
                    "Final Reminder: Task due in 30 minutes",
                    "Your task \"" + todo.getTitle() + "\" is due at " + todo.getDueDate());
        }
    }

    // Run daily at 1 AM to check missed tasks
    @Scheduled(cron = "0 0 1 * * *")
    public void sendMissedNotifications() {
        List<Todo> missedTodos = todoService.getMissedTodos();
        for (Todo todo : missedTodos) {
            emailService.sendEmail(todo.getUser().getEmail(),
                    "Missed Task Notification",
                    "You missed the task \"" + todo.getTitle() + "\" which was due on " + todo.getDueDate());
        }
    }

    @Scheduled(cron = "0 */1 * * * *") // Runs every 1 minute
    public void sendPeriodicEmail() {
        // Fetch all tasks that are not due yet and are due in the future
        List<Todo> futureTodos = todoService.getTodosNotDueYet();

        for (Todo todo : futureTodos) {
            String recipient = todo.getUser().getEmail(); // Assuming Todo has a reference to a User
            String subject = "Upcoming Task Reminder";
            String message = "Your task \"" + todo.getTitle() + "\" is due on " + todo.getDueDate() + ". Don't forget to complete it!";

            // Send email
            emailService.sendEmail(recipient, subject, message);

            System.out.println("Email sent to: " + recipient + " for task: " + todo.getTitle() + " at " + java.time.LocalDateTime.now());
        }
    }
}