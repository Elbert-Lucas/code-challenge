package br.com.hr_system.notification.controller;

import br.com.hr_system.notification.domain.Notification;
import br.com.hr_system.notification.dto.NotificationDto;
import br.com.hr_system.notification.dto.NotificationResponseDto;
import br.com.hr_system.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping("/notification/post")
    @SendTo("/notification/receive")
    public NotificationResponseDto postNotification(NotificationDto notification, Principal principal){
        log.debug("Iniciando post de notificação");
        return notificationService.postNotification(notification, principal);
    }
    @GetMapping("/received")
    public ResponseEntity<Page<NotificationResponseDto>> getReceived(Pageable pageable){
        return new ResponseEntity<>(notificationService.findReceived(pageable), HttpStatus.OK);
    }
    @GetMapping("/sent")
    public ResponseEntity<Page<NotificationResponseDto>> getSent(Pageable pageable){
        return new ResponseEntity<>(notificationService.findSent(pageable), HttpStatus.OK);
    }
}

