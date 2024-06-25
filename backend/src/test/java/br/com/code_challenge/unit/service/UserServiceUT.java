package br.com.code_challenge.unit.service;

import br.com.code_challenge.domain.Address;
import br.com.code_challenge.domain.User;
import br.com.code_challenge.dto.request.RegisterUserDTO;
import br.com.code_challenge.repository.RoleRepository;
import br.com.code_challenge.repository.UserRepository;
import br.com.code_challenge.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceUT {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    static RegisterUserDTO registerUserDTO;

    @BeforeAll
    static void setup() throws IOException {
        String modelsPath = "src/test/resources/models/";
        String registerUserPath = modelsPath + "user-register.json";
        registerUserDTO =  new ObjectMapper().readValue(new File(registerUserPath), RegisterUserDTO.class);
    }
    @Autowired
    private Environment environment;

    @Test
    void shouldRegisterUserSuccessfully(){
        assertEquals(HttpStatus.CREATED, userService.registerUser(registerUserDTO).getStatus());
    }

    @Test
    void shouldThrowExceptionOnDuplication() throws IOException {
        String modelsPath = "src/test/resources/models/";
        String registerUserPath = modelsPath + "user-register.json";
        RegisterUserDTO duplicateDto =  new ObjectMapper().readValue(new File(registerUserPath), RegisterUserDTO.class);
        assertThrows(DataIntegrityViolationException.class, () -> userService.registerUser(duplicateDto));
    }

    @Test
    void shouldThrowExceptionOnInvalidData(){
        registerUserDTO.setAddress(new Address());
        registerUserDTO.setEmail("Test@test.com");
        registerUserDTO.setPhone("999999999999999999999");
        assertThrows(DataIntegrityViolationException.class, () -> userService.registerUser(registerUserDTO));
    }
}
