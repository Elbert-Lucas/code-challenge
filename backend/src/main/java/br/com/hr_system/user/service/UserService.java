package br.com.hr_system.user.service;

import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.dto.RegisterUserDto;
import br.com.hr_system.user.mapper.UserMapper;
import br.com.hr_system.user.util.UserEmails;
import br.com.hr_system.shared.dto.CreatedMessageResponseDTO;
import br.com.hr_system.shared.dto.abstracts.MessageResponseDTO;
import br.com.hr_system.user.repository.UserRepository;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserEmails userEmails;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, UserEmails userEmails) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userEmails = userEmails;
    }

    @Transient
    public MessageResponseDTO registerUser(RegisterUserDto dto){
        User user = userMapper.dtoToEntity(dto);
        userRepository.save(user);
        userEmails.sendToRegisteredUser(user);
        return new CreatedMessageResponseDTO("Usuário criado com sucesso");
    }

    private void sendEmailToRegisteredUser(){}
    public User searchUser(Long userId) {
        return userRepository.findById(userId).get();
    }
}
