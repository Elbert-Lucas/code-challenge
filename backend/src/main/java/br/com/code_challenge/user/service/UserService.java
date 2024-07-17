package br.com.code_challenge.user.service;

import br.com.code_challenge.user.domain.User;
import br.com.code_challenge.user.dto.RegisterUserDto;
import br.com.code_challenge.user.mapper.UserMapper;
import br.com.code_challenge.user.util.UserEmails;
import br.com.code_challenge.shared.dto.CreatedMessageResponseDTO;
import br.com.code_challenge.shared.dto.abstracts.MessageResponseDTO;
import br.com.code_challenge.user.repository.UserRepository;
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
