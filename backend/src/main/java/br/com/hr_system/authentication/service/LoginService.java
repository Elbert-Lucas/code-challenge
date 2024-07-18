package br.com.hr_system.authentication.service;

import br.com.hr_system.authentication.dto.CredentialsDto;
import br.com.hr_system.authentication.dto.LoginDTO;
import br.com.hr_system.authentication.exception.InvalidUsernameOrPasswordException;
import br.com.hr_system.config.security.JwtUtil;
import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.mapper.UserMapper;
import br.com.hr_system.user.service.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtService;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginService(UserDetailsService userDetailsService, JwtUtil jwtService, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
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
        return userDetailsService.findUserByEmail(username)
                             .orElseThrow(InvalidUsernameOrPasswordException::new);
    }
    private void validatePassword(String passwordDTO, User user){
        if (!passwordEncoder.matches(passwordDTO, user.getPassword())) throw new InvalidUsernameOrPasswordException();
    }


}
