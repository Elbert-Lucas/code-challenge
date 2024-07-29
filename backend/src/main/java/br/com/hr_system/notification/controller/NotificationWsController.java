package br.com.hr_system.notification.controller;

import br.com.hr_system.notification.domain.view.ResponseNotification;
import br.com.hr_system.notification.dto.NotificationDto;
import br.com.hr_system.notification.dto.ResponseView;
import br.com.hr_system.notification.service.NotificationSaveService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
public class NotificationWsController {

    private final NotificationSaveService notificationSaveService;

    @Autowired
    public NotificationWsController(NotificationSaveService notificationSaveService) {
        this.notificationSaveService = notificationSaveService;
    }

    @MessageMapping("/notification/post")
    @SendTo("/notification/receive")
    @JsonView(ResponseView.Receive.class)
    public ResponseNotification postNotification(NotificationDto notification, Principal principal){
        log.debug("Iniciando post de notificação");
        return notificationSaveService.postNotification(notification, principal);
    }
}

