package br.com.hr_system.notification.controller;

import br.com.hr_system.notification.domain.view.ResponseNotification;
import br.com.hr_system.notification.dto.ResponseView;
import br.com.hr_system.notification.service.NotificationDetailsService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationRestController {

    private final NotificationDetailsService notificationDetailsService;

    @Autowired
    public NotificationRestController(NotificationDetailsService notificationDetailsService) {
        this.notificationDetailsService = notificationDetailsService;
    }

    @JsonView(ResponseView.Receive.class)
    @GetMapping("/received")
    public ResponseEntity<Page<ResponseNotification>> getReceived(Pageable pageable){
        return new ResponseEntity<>(notificationDetailsService.findReceived(pageable), HttpStatus.OK);
    }
    @JsonView(ResponseView.Sent.class)
    @GetMapping("/sent")
    public ResponseEntity<Page<ResponseNotification>> getSent(Pageable pageable){
        return new ResponseEntity<>(notificationDetailsService.findSent(pageable), HttpStatus.OK);
    }
}
