package br.com.hr_system.unit.config;

import br.com.hr_system.config.security.AuthenticationService;
import br.com.hr_system.config.security.JwtUtil;
import br.com.hr_system.config.security.exception.InvalidTokenException;
import br.com.hr_system.user.domain.Address;
import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.dto.RegisterUserDto;
import br.com.hr_system.user.enums.UserStatus;
import br.com.hr_system.user.mapper.UserMapper;
import br.com.hr_system.user.repository.UserRepository;
import br.com.hr_system.user.service.UserUpdatesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AuthenticationServiceUT {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserUpdatesService updatesService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    JwtUtil jwtUtil;

    static RegisterUserDto registerUserDto;

    static User user;
    static String token;
    static String refreshToken;

    @BeforeAll
    static void setup() throws IOException {
        String modelsPath = "src/test/resources/models/";
        String registerUserPath = modelsPath + "user-register.json";
        registerUserDto =  new ObjectMapper().readValue(new File(registerUserPath), RegisterUserDto.class);
    }

    @BeforeEach
    void saveUser(){
        updatesService.registerUser(registerUserDto);
        user = userRepository.findUserByEmail(registerUserDto.getEmail()).get();
        token = jwtUtil.createUserTokens(userMapper.entityToLoggedDTO(user)).get("token");
        refreshToken = jwtUtil.createUserTokens(userMapper.entityToLoggedDTO(user)).get("refreshToken");
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        request.setAttribute("user", userMapper.entityToLoggedDTO(user));
    }
    @AfterEach
    void deleteUser() throws IOException {
        userRepository.findUserByEmail(registerUserDto.getEmail()).ifPresent(user -> {
            userRepository.delete((user));
        });
        updateUserAddress();
    }
    private void updateUserAddress() throws IOException {
        Address address = new ObjectMapper().readValue(new File("src/test/resources/models/address.json"), Address.class);
        address.setAddress(address.getAddress() + ", " + new Random().nextInt());
        registerUserDto.setAddress(address);
    }

    @Test
    void shouldAuthenticateSuccessfully(){
       assertDoesNotThrow(() -> authenticationService.authenticate(token, false));
    }
    @Test
    void shouldThrownWhenNotValid() throws IOException {
        final String INVALID_TOKEN = new ObjectMapper().readValue(new File("src/test/resources/models/invalid-token.txt"), String.class);
        assertThrows(SignatureException.class, () -> authenticationService.authenticate(INVALID_TOKEN, false));
    }
    @Test
    void shouldThrowWhenExpired() throws IOException {
        final String EXPIRED_TOKEN = new ObjectMapper().readValue(new File("src/test/resources/models/expired-token.txt"), String.class);
        assertThrows(ExpiredJwtException.class, () -> authenticationService.authenticate(EXPIRED_TOKEN, false));
    }
    @Test
    void shouldThrownWhenUserNotActive() {
        user.setStatus(UserStatus.DISABLE);
        userRepository.save(user);
        assertThrows(InvalidTokenException.class, () -> authenticationService.authenticate(token, false));
    }
    @Test
    void shouldRefreshTokenSuccessfully(){
        assertDoesNotThrow(() -> jwtUtil.refreshToken(refreshToken, userMapper.entityToLoggedDTO(user)));
    }
    @Test
    void shouldThrownWhenRefreshTokenInvalid() throws IOException {
        final String INVALID_TOKEN = new ObjectMapper().readValue(new File("src/test/resources/models/invalid-token.txt"), String.class);
        assertThrows(SignatureException.class, () -> jwtUtil.refreshToken(INVALID_TOKEN, userMapper.entityToLoggedDTO(user)));
    }
    @Test
    void shouldAuthenticateRegisterSuccessfully(){
        user.setStatus(UserStatus.PASSWORD_PENDING);
        userRepository.save(user);
        String registerToken = jwtUtil.createRegisterPasswordToken(userMapper.entityToBasicDTO(user));
        assertDoesNotThrow(() -> authenticationService.authenticate(registerToken, true));
    }
    @Test
    void shouldThrownOnRegisterWhenUserNotPasswordPending(){
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        String registerToken = jwtUtil.createRegisterPasswordToken(userMapper.entityToBasicDTO(user));
        assertThrows(InvalidTokenException.class, () -> authenticationService.authenticate(registerToken, true));
    }
}
