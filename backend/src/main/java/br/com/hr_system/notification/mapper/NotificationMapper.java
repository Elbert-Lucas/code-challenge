package br.com.hr_system.notification.mapper;

import br.com.hr_system.notification.domain.Notification;
import br.com.hr_system.notification.dto.NotificationDto;
import br.com.hr_system.notification.dto.NotificationResponseDto;
import br.com.hr_system.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationMapper {

    private final UserRepository userRepository;

    @Autowired
    public NotificationMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Notification dtoToEntity(NotificationDto dto) {
        Notification notification = new ModelMapper().map(dto, Notification.class);
        setUsersOnNotification(dto, notification);
        return notification;
    }
    private void setUsersOnNotification(NotificationDto dto, Notification notification){
        notification.setFrom(userRepository.findById(dto.getFrom()).get());

        if(!dto.getToAll()){
            if(dto.getUsers().isEmpty()) throw new RuntimeException("Notificações precisam ser enviadas para ao menos 1 pessoa");
            else notification.setUsers(userRepository.findMultipleUsersByIds(dto.getUsers()));
        }
    }

    public NotificationResponseDto entityToResponseDto(Notification notification) {
        NotificationResponseDto responseDto = new ModelMapper().map(notification, NotificationResponseDto.class);
        responseDto.setFrom(notification.getFrom().getName());
        return responseDto;
    }
}
