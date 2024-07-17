package br.com.hr_system.shared.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessagesUtil {

    private final MessageSource messageSource;

    private static final Locale LOCALE = new Locale("pt", "BR");

    public MessagesUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String title){
        return messageSource.getMessage(title, null, LOCALE);
    }
}
