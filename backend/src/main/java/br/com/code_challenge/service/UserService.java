package br.com.code_challenge.service;

import br.com.code_challenge.domain.User;
import br.com.code_challenge.dto.request.RegisterUserDTO;
import br.com.code_challenge.dto.response.CreatedMessageResponseDTO;
import br.com.code_challenge.dto.response.abstracts.MessageResponseDTO;
import br.com.code_challenge.enums.UserRole;
import br.com.code_challenge.enums.UserStatus;
import br.com.code_challenge.repository.RoleRepository;
import br.com.code_challenge.repository.UserRepository;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    public void UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transient
    public MessageResponseDTO registerUser(RegisterUserDTO dto){
        User user = dto.toEntity();
        user.setRole(roleRepository.findByRole(UserRole.BASIC).get());
        userRepository.save(user);
        return new CreatedMessageResponseDTO("Usuário criado com sucesso");
    }
}
