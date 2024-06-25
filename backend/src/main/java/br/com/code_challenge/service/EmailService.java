package br.com.code_challenge.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
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

    public void sendEmail(String to, String name){
        try {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("elbertlucas0@gmail.com");
        helper.setText(manageHtml(name), true);

        sender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    private String manageHtml(String name){
        File html = new File("src/main/resources/templates/register-user-email.html");
        StringBuilder htmlSb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(html)))) {
            String line;
            while ((line = br.readLine()) != null) {
                htmlSb.append(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return htmlSb.toString().replace("${name}", name);
    }
}
