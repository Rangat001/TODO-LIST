package com.rgt.todolist.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceIntegrationTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendEmail() {
        // Arrange
        String to = "rgtwork114@gmail.com";
        String subject = "Test Email";
        String message = "This is a test email from the integration test.";

        // Act
        emailService.sendEmail(to, subject, message);

        // There is no assertion here because you need to manually verify that the email is received
        System.out.println("Email sent. Please check your inbox.");
    }
}