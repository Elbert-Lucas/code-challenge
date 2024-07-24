package br.com.hr_system.shared.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class EmailService{

    private final JavaMailSender sender;

    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendEmail(EmailModel model){
        try {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(model.getTo());
        helper.setFrom(model.getFROM());
        helper.setSubject(model.getSubject());
        helper.setText(model.getMessage(), model.isHtml());

        sender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter @Setter
    public static class EmailModel{
        private String to;
        private final String FROM = "hr-system@contact.com";
        private String subject;
        private String message;
        private boolean html;
    }
}
