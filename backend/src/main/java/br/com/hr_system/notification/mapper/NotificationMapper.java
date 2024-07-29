package br.com.hr_system.notification.mapper;

import br.com.hr_system.notification.domain.Notification;
import br.com.hr_system.notification.domain.view.ResponseNotification;
import br.com.hr_system.notification.dto.NotificationDto;
import br.com.hr_system.user.mapper.UserMapper;
import br.com.hr_system.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    public NotificationMapper(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
            else notification.setTo(userRepository.findMultipleUsersByIds(dto.getUsers()));
        }
    }

    public ResponseNotification entityToResponseDto(Notification notification) {
        ResponseNotification responseDto = new ModelMapper().map(notification, ResponseNotification.class);
        responseDto.setFrom(userMapper.entityToSimpleDTO(notification.getFrom()));
        responseDto.setToAll(notification.getToAll());
        if(!notification.getToAll())responseDto.setTo(notification.getToUsers().stream().map(userMapper::entityToSimpleDTO).toList());
        return responseDto;
    }
}
