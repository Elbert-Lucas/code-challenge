package br.com.hr_system.notification.service;

import br.com.hr_system.notification.domain.Notification;
import br.com.hr_system.notification.dto.NotificationDto;
import br.com.hr_system.notification.dto.NotificationResponseDto;
import br.com.hr_system.notification.mapper.NotificationMapper;
import br.com.hr_system.notification.repository.NotificationRepository;
import br.com.hr_system.notification.repository.NotificationInserterRepository;
import br.com.hr_system.session.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
public class NotificationSaveService {
    private final NotificationInserterRepository notificationInserter;
    private final NotificationMapper notificationMapper;

    private final SessionService sessionService;

    private final SimpMessagingTemplate simpMessaging;


    private final String USER_PATH = "/notification/me";
    @Autowired
    public NotificationSaveService(NotificationMapper notificationMapper, NotificationInserterRepository notificationInserter,
                                   SessionService sessionService, SimpMessagingTemplate simpMessaging) {
        this.notificationMapper = notificationMapper;
        this.notificationInserter = notificationInserter;
        this.sessionService = sessionService;
        this.simpMessaging = simpMessaging;
    }

    @Transactional
    public NotificationResponseDto postNotification(NotificationDto notificationDto, Principal principal){
        notificationDto.setFrom(findFromUser(principal));
        Notification notification = saveNotification(notificationDto);
        NotificationResponseDto responseDto = notificationMapper.entityToResponseDto(notification);
        if(notificationDto.getToAll()) {
            return responseDto;
        }else return sendNotificationToUsers(notificationDto, responseDto);
    }

    private Integer findFromUser(Principal principal){
       return sessionService.findUserIdBySessionId(principal.getName());
    }
    private Notification saveNotification(NotificationDto notificationDto){
        Notification notification = notificationMapper.dtoToEntity(notificationDto);
        Integer notificationId = notificationInserter.insertNotification(notification);
        notification.setId(notificationId);
        return notification;
    }
    private NotificationResponseDto sendNotificationToUsers(NotificationDto notificationDto, NotificationResponseDto responseDto) {
        List<String> sessions = sessionService.findSessionByUserId(notificationDto.getUsers().stream().map(Object::toString).toList());
        sessions.forEach(session -> {
            this.simpMessaging.convertAndSendToUser(session,
                                                    this.USER_PATH,
                                                    responseDto);
                                                    });
        return null;
    }

}
