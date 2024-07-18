package br.com.hr_system.authentication.service;

import br.com.hr_system.authentication.dto.CredentialsDto;
import br.com.hr_system.authentication.dto.LoginDTO;
import br.com.hr_system.authentication.exception.InvalidUsernameOrPasswordException;
import br.com.hr_system.config.security.JwtUtil;
import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.dto.LoggedUserDetailsDto;
import br.com.hr_system.user.mapper.UserMapper;
import br.com.hr_system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    private final UserRepository userRepository;
    private final JwtUtil jwtService;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginService(UserRepository userRepository, JwtUtil jwtService, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public CredentialsDto doLogin(LoginDTO loginDTO){
        User user = findUser(loginDTO.getUsername());
        validatePassword(loginDTO.getPassword(), user);
        return new CredentialsDto(jwtService.createUserTokens(userMapper.entityToLoggedDTO(user)));
    }

    private User findUser(String username){
        return userRepository.findUserByEmail(username)
                             .orElseThrow(InvalidUsernameOrPasswordException::new);
    }
    private void validatePassword(String passwordDTO, User user){
        if (!passwordEncoder.matches(passwordDTO, user.getPassword())) throw new InvalidUsernameOrPasswordException();
    }


}
