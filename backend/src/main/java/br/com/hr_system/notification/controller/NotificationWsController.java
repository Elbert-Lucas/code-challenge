package br.com.hr_system.notification.controller;

import br.com.hr_system.notification.dto.NotificationDto;
import br.com.hr_system.notification.dto.NotificationResponseDto;
import br.com.hr_system.notification.service.NotificationSaveService;
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
    public NotificationResponseDto postNotification(NotificationDto notification, Principal principal){
        log.debug("Iniciando post de notificação");
        return notificationSaveService.postNotification(notification, principal);
    }
}

