package br.com.hr_system.notification.service;

import br.com.hr_system.notification.domain.Notification;
import br.com.hr_system.notification.dto.NotificationDto;
import br.com.hr_system.notification.mapper.NotificationMapper;
import br.com.hr_system.notification.repository.NotificationRepository;
import br.com.hr_system.notification.repository.NotificationInserterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

    private final NotificationMapper notificationMapper;

    private final NotificationRepository notificationRepository;
    private final NotificationInserterRepository notificationInserter;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, NotificationMapper notificationMapper, NotificationInserterRepository notificationInserter) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.notificationInserter = notificationInserter;
    }

    @Transactional
    public Notification postNotification(NotificationDto notificationDto){
        Notification notification = notificationMapper.dtoToEntity(notificationDto);
        notification.setId(notificationInserter.insertNotification(notification));

        return notification;
    }
}
