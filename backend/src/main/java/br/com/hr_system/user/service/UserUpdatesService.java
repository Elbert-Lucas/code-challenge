package br.com.hr_system.user.service;

import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.dto.PasswordUpdateDto;
import br.com.hr_system.user.dto.RegisterUserDto;
import br.com.hr_system.user.enums.UserStatus;
import br.com.hr_system.user.mapper.UserMapper;
import br.com.hr_system.user.util.UserEmails;
import br.com.hr_system.shared.dto.CreatedMessageResponseDTO;
import br.com.hr_system.shared.dto.abstracts.MessageResponseDTO;
import br.com.hr_system.user.repository.UserRepository;
import jakarta.persistence.Transient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserUpdatesService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserEmails userEmails;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserUpdatesService(UserRepository userRepository, UserMapper userMapper, UserEmails userEmails, UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userEmails = userEmails;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transient
    public MessageResponseDTO registerUser(RegisterUserDto dto){
        User user = userMapper.dtoToEntity(dto);
        userRepository.save(user);
        userEmails.sendToRegisteredUser(user);
        return new CreatedMessageResponseDTO("Usuário criado com sucesso");
    }

    @Transient
    public MessageResponseDTO updatePassword(PasswordUpdateDto passwordDto){
        User user = userDetailsService.findLoggedUser();
        validatePassword(user, passwordDto);
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        return new CreatedMessageResponseDTO("Senha atualizada com sucesso");
    }
    private void validatePassword(User user, PasswordUpdateDto passwordDto){
        if(user.getStatus().equals(UserStatus.PASSWORD_PENDING)) return;
        else if(passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())
            && passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())){
            return;
        }else if(!passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())){
            log.error("A senha antiga não confere com a senha passada");
            throw new RuntimeException();
        }else{
            log.error("As novas senhas não conferem");
            throw new RuntimeException();
        }
    }

}
