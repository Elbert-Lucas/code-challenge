package br.com.hr_system.notification.service;

import br.com.hr_system.notification.domain.view.ResponseNotification;
import br.com.hr_system.notification.repository.NotificationResponseRepository;
import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.mapper.UserMapper;
import br.com.hr_system.user.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class NotificationDetailsService {
    private final UserDetailsService userDetailsService;
    private final NotificationResponseRepository responseRepository;
    private final UserMapper userMapper;
    @Autowired
    public NotificationDetailsService(UserDetailsService userDetailsService, NotificationResponseRepository responseRepository, UserMapper userMapper) {
        this.userDetailsService = userDetailsService;
        this.responseRepository = responseRepository;
        this.userMapper = userMapper;
    }
    public Page<ResponseNotification> findReceived(Pageable pageable) {
        User loggedUser = userDetailsService.findLoggedUser();
        return responseRepository.findAllByToOrToAllTrueOrderByIdDesc(userMapper.entityToSimpleDTO(loggedUser), pageable);
    }

    public Page<ResponseNotification> findSent(Pageable pageable) {
        User loggedUser = userDetailsService.findLoggedUser();
        return responseRepository.findAllByFrom_OrderByIdDesc(userMapper.entityToSimpleDTO(loggedUser), pageable);
    }
}
