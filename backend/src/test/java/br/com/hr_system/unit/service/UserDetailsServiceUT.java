package br.com.hr_system.unit.service;

import br.com.hr_system.config.security.JwtUtil;
import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.domain.view.LoggedUserDetails;
import br.com.hr_system.user.dto.RegisterUserDto;
import br.com.hr_system.user.mapper.UserMapper;
import br.com.hr_system.user.repository.UserRepository;
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
public class UserDetailsServiceUT {

    @Autowired
    UserDetailsService userDetailsService;
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
    private static boolean isUserAlreadySaved = false;

    @BeforeAll
    static void setup() throws IOException {
        String modelsPath = "src/test/resources/models/";
        String registerUserPath = modelsPath + "user-register.json";
        registerUserDto =  new ObjectMapper().readValue(new File(registerUserPath), RegisterUserDto.class);
    }

    @BeforeEach
    void saveUser(){
        if(!isUserAlreadySaved){
            updatesService.registerUser(registerUserDto);
            user = userRepository.findUserByEmail(registerUserDto.getEmail()).get();
            token = jwtUtil.createUserTokens(userMapper.entityToLoggedDTO(user)).get("token");
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            request.setAttribute("user", userMapper.entityToLoggedDTO(user));
            isUserAlreadySaved = true;
        }
    }
    @Test
    void shouldFindUserByToken(){
        LoggedUserDetails loggedUser = userDetailsService.findUserByToken(token);
        assertEquals(user.getEmail(), loggedUser.getEmail());
        assertEquals(user.getName(), loggedUser.getName());
        assertEquals(user.getBirth(), loggedUser.getBirth());
    }
    @Test
    void shouldFindLoggedUser(){
        User loggedUser = userDetailsService.findLoggedUser();
        assertEquals(user.getEmail(), loggedUser.getEmail());
        assertEquals(user.getName(), loggedUser.getName());
        assertEquals(user.getBirth(), loggedUser.getBirth());
    }
    @Test
    void shouldFindUserByEmail(){
        User userByEmail = userDetailsService.findUserByEmail(user.getEmail()).get();
        assertEquals(user.getEmail(), userByEmail.getEmail());
        assertEquals(user.getName(), userByEmail.getName());
        assertEquals(user.getBirth(), userByEmail.getBirth());
    }
}
