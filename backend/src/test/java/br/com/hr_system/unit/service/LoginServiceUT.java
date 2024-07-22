package br.com.hr_system.unit.service;


import br.com.hr_system.authentication.dto.CredentialsDto;
import br.com.hr_system.authentication.dto.LoginDTO;
import br.com.hr_system.authentication.exception.InvalidUsernameOrPasswordException;
import br.com.hr_system.authentication.service.LoginService;
import br.com.hr_system.config.security.JwtUtil;
import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.dto.PasswordUpdateDto;
import br.com.hr_system.user.dto.RegisterUserDto;
import br.com.hr_system.user.mapper.UserMapper;
import br.com.hr_system.user.service.UserDetailsService;
import br.com.hr_system.user.service.UserUpdatesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class LoginServiceUT {

    @Autowired
    LoginService loginService;
    @Autowired
    UserUpdatesService userUpdatesService;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserUpdatesService updatesService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    JwtUtil jwtUtil;

    static RegisterUserDto registerUserDto;
    static User user;
    static LoginDTO loginDTO;

    private static boolean isUserAlreadySaved = false;

    @BeforeAll
    static void setup() throws IOException {
        String modelsPath = "src/test/resources/models/";
        String registerUserPath = modelsPath + "user-register.json";
        registerUserDto =  new ObjectMapper().readValue(new File(registerUserPath), RegisterUserDto.class);

        String loginDtoPath = modelsPath + "user-login.json";
        loginDTO = new ObjectMapper().readValue(new File(loginDtoPath), LoginDTO.class);
    }

    @BeforeEach
    void saveUser() throws IOException {
        if(!isUserAlreadySaved){
            updatesService.registerUser(registerUserDto);
            user = userDetailsService.findUserByEmail(registerUserDto.getEmail()).get();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            request.setAttribute("user", userMapper.entityToLoggedDTO(user));

            PasswordUpdateDto passwordDto = new ObjectMapper().readValue(new File("src/test/resources/models/change-password.json"), PasswordUpdateDto.class);
            userUpdatesService.updatePassword(passwordDto);
            user = userDetailsService.findUserByEmail(registerUserDto.getEmail()).get();
            isUserAlreadySaved = true;
        }
    }
    @Test
    void shouldLoginSuccessfully(){
        assertDoesNotThrow(() -> loginService.doLogin(loginDTO));
        CredentialsDto credentialsDto = loginService.doLogin(loginDTO);
        assertDoesNotThrow(() -> jwtUtil.validateToken(credentialsDto.getToken(), userMapper.entityToLoggedDTO(user)));
        assertDoesNotThrow(() -> jwtUtil.refreshToken(credentialsDto.getRefreshToken(), userMapper.entityToLoggedDTO(user)));
    }
    @Test
    void shouldRejectLoginOnInvalidPassword(){
        LoginDTO invalidLoginDTO = loginDTO;
        invalidLoginDTO.setPassword("Invalida");
        assertThrows(InvalidUsernameOrPasswordException.class, () -> loginService.doLogin(invalidLoginDTO));
    }
    @Test
    void shouldRejectLoginOnInvalidUsername(){
        LoginDTO invalidLoginDTO = loginDTO;
        invalidLoginDTO.setUsername("Invalido");
        assertThrows(InvalidUsernameOrPasswordException.class, () -> loginService.doLogin(invalidLoginDTO));
    }
}
