package br.com.code_challenge.user.util;

import br.com.code_challenge.user.domain.User;
import br.com.code_challenge.shared.service.EmailService;
import br.com.code_challenge.shared.service.EmailService.EmailModel;
import br.com.code_challenge.shared.util.MessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserEmails {

    private final EmailService emailService;

    private final MessagesUtil messages;

    @Autowired
    public UserEmails(EmailService emailService, MessagesUtil messages){
        this.emailService = emailService;
        this.messages = messages;
    }

    public void sendToRegisteredUser(User user) {
        emailService.sendEmail(createRegisteredModel(user));
    }

    private EmailModel createRegisteredModel(User user){
        EmailModel model = new EmailModel();
        model.setTo(user.getEmail());
        model.setSubject(messages.getMessage("email.register-subject"));
        model.setTo(user.getEmail());

        model.setHtml(true);
        Map<String, String> replaces = new HashMap<>();
        replaces.put("${name}", user.getName().split(" ")[0]);
        String message = manageHtml("src/main/resources/templates/register-user-email.html",replaces);
        model.setMessage(message);
        return model;
    }
    private String manageHtml(String htmlPath, Map<String, String> replaces){
        File html = new File(htmlPath);
        StringBuilder htmlSb = new StringBuilder();
        String htmlStr;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(html)))) {
            String line;
            while ((line = br.readLine()) != null) {
                htmlSb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        htmlStr = htmlSb.toString();
        for(String key : replaces.keySet()){
            htmlStr = htmlStr.replace(key, replaces.get(key));
        }
        return htmlStr;
    }
}
