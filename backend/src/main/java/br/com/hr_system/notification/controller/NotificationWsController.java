package br.com.hr_system.notification.controller;

import br.com.hr_system.notification.domain.view.ResponseNotification;
import br.com.hr_system.notification.dto.NotificationDto;
import br.com.hr_system.notification.service.NotificationSaveService;
import br.com.hr_system.user.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class NotificationWsController {

    private final static List<String> ALLOWED_ROLES =List.of("ROLE_"+ UserRole.ADMIN,
                                                             "ROLE_"+ UserRole.SUPER_ADMIN,
                                                             "ROLE_"+ UserRole.OWNER);

    private final NotificationSaveService notificationSaveService;

    @Autowired
    public NotificationWsController(NotificationSaveService notificationSaveService) {
        this.notificationSaveService = notificationSaveService;
    }

    @MessageMapping("/notification/post")
    @SendTo("/notification/receive")
    @JsonView(ResponseNotification.Receive.class)
    public ResponseNotification postNotification(@Payload NotificationDto notification,
                                                 SimpMessageHeaderAccessor headerAccessor) throws IllegalAccessException {
        if(!ALLOWED_ROLES.contains(headerAccessor.getSessionAttributes().get("role"))) throw new IllegalAccessException();

        log.debug("Iniciando post de notificação");
        return notificationSaveService.postNotification(notification, headerAccessor.getUser());
    }
}

