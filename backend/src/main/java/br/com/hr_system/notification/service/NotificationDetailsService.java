package br.com.hr_system.notification.service;

import br.com.hr_system.notification.dto.NotificationResponseDto;
import br.com.hr_system.notification.mapper.NotificationMapper;
import br.com.hr_system.notification.repository.NotificationRepository;
import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NotificationDetailsService {
    private final UserDetailsService userDetailsService;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Autowired
    public NotificationDetailsService(UserDetailsService userDetailsService, NotificationRepository notificationRepository,
                                      NotificationMapper notificationMapper) {
        this.userDetailsService = userDetailsService;
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }
    public Page<NotificationResponseDto> findReceived(Pageable pageable) {
        User loggedUser = userDetailsService.findLoggedUser();
        return notificationRepository.findAllByUsers_OrToAllIsTrue(loggedUser, pageable)
                .map(notificationMapper::entityToResponseDto);
    }
    public Page<NotificationResponseDto> findSent(Pageable pageable) {
        User loggedUser = userDetailsService.findLoggedUser();
        return notificationRepository.findAllByFrom(loggedUser, pageable)
                .map(notificationMapper::entityToResponseDto);
    }
}
