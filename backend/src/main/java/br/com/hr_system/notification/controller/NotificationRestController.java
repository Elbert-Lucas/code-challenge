package br.com.hr_system.notification.controller;

import br.com.hr_system.notification.dto.NotificationResponseDto;
import br.com.hr_system.notification.service.NotificationDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationRestController {

    private final NotificationDetailsService notificationDetailsService;

    @Autowired
    public NotificationRestController(NotificationDetailsService notificationDetailsService) {
        this.notificationDetailsService = notificationDetailsService;
    }

    @GetMapping("/received")
    public ResponseEntity<Page<NotificationResponseDto>> getReceived(Pageable pageable){
        return new ResponseEntity<>(notificationDetailsService.findReceived(pageable), HttpStatus.OK);
    }
    @GetMapping("/sent")
    public ResponseEntity<Page<NotificationResponseDto>> getSent(Pageable pageable){
        return new ResponseEntity<>(notificationDetailsService.findSent(pageable), HttpStatus.OK);
    }
}
