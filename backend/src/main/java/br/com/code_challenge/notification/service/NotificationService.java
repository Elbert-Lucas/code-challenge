package br.com.code_challenge.notification.service;

import br.com.code_challenge.notification.domain.Notification;
import br.com.code_challenge.notification.dto.NotificationDto;
import br.com.code_challenge.shared.dto.CreatedMessageResponseDTO;
import br.com.code_challenge.shared.dto.abstracts.MessageResponseDTO;
import br.com.code_challenge.notification.repository.NotificationRepository;
import br.com.code_challenge.user.repository.UserRepository;
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
