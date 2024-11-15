package br.com.hr_system.user.util;

import br.com.hr_system.config.security.JwtUtil;
import br.com.hr_system.user.domain.User;
import br.com.hr_system.shared.service.EmailService;
import br.com.hr_system.shared.service.EmailService.EmailModel;
import br.com.hr_system.shared.util.MessagesUtil;
import br.com.hr_system.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserEmails {

    private final EmailService emailService;
    private final MessagesUtil messages;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @Autowired
    public UserEmails(EmailService emailService, MessagesUtil messages, JwtUtil jwtUtil, UserMapper userMapper){
        this.emailService = emailService;
        this.messages = messages;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
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
        replaces.put("${redirect-link}", "http://localhost:4200/register-me?auth="+jwtUtil.createRegisterPasswordToken(userMapper.entityToBasicDTO(user)));
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
