package com.example.email_notifications_service.usecasses;


import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void sendEmailWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            File attachment = new File(pathToAttachment);
            if (attachment.exists()) {
                helper.addAttachment(attachment.getName(), attachment);
            } else {
                log.error("Attachment file not found: {}", pathToAttachment);
                throw new FileNotFoundException("Attachment file not found: " + pathToAttachment);
            }

            emailSender.send(message);
            log.info("Email sent to {} with attachment {}", to, pathToAttachment);

        } catch (FileNotFoundException e) {
            log.error("Attachment file not found: {}", pathToAttachment, e);
            throw new MessagingException("Attachment file not found: " + pathToAttachment, e);
        } catch (jakarta.mail.MessagingException e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage(), e);
            throw new MessagingException("Failed to send email to " + to + ": " + e.getMessage(), e);
        }
    }
}
