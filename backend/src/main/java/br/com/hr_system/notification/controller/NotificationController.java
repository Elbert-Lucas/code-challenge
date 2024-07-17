package br.com.hr_system.notification.controller;

import br.com.hr_system.notification.dto.NotificationDto;
import br.com.hr_system.shared.dto.abstracts.MessageResponseDTO;
import br.com.hr_system.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponseDTO> postNotification(@RequestBody @Valid NotificationDto notificationDto){
        log.debug("Iniciando post de notificação");
        MessageResponseDTO response = notificationService.postNotification(notificationDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

