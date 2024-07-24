package br.com.hr_system.notification.controller;

import br.com.hr_system.notification.domain.Notification;
import br.com.hr_system.notification.dto.NotificationDto;
import br.com.hr_system.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping("/notification/post")
    @SendTo("/notification/receive")
    public Notification postNotification(NotificationDto notification){
        log.debug("Iniciando post de notificação");
        return notificationService.postNotification(notification);
    }
}

