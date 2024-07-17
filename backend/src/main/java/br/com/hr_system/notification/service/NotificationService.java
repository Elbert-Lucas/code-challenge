package br.com.hr_system.notification.service;

import br.com.hr_system.notification.domain.Notification;
import br.com.hr_system.notification.dto.NotificationDto;
import br.com.hr_system.shared.dto.CreatedMessageResponseDTO;
import br.com.hr_system.shared.dto.abstracts.MessageResponseDTO;
import br.com.hr_system.notification.repository.NotificationRepository;
import br.com.hr_system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public MessageResponseDTO postNotification(NotificationDto notificationDto){
        Notification notification = notificationDto.toEntity();
        setUsersOnNotification(notificationDto, notification);
        notificationRepository.save(notification);
        return new CreatedMessageResponseDTO("Notificação postada com sucesso");
    }
    private void setUsersOnNotification(NotificationDto dto, Notification notification){
        notification.setFrom(userRepository.findById(dto.getFromId()).get());
        notification.setUsers(userRepository.findMultipleUsersByIds(dto.getUsers()));
    }
}
