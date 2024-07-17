package br.com.code_challenge.unit.service;

import br.com.code_challenge.domain.Address;
import br.com.code_challenge.dto.request.RegisterUserDto;
import br.com.code_challenge.repository.UserRepository;
import br.com.code_challenge.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceUT {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    static RegisterUserDto registerUserDto;

    @BeforeAll
    static void setup() throws IOException {
        String modelsPath = "src/test/resources/models/";
        String registerUserPath = modelsPath + "user-register.json";
        registerUserDto =  new ObjectMapper().readValue(new File(registerUserPath), RegisterUserDto.class);
    }
    @Autowired
    private Environment environment;

    @Test
    void shouldRegisterUserSuccessfully(){
        assertEquals(HttpStatus.CREATED, userService.registerUser(registerUserDto).getStatus());
    }

    @Test
    void shouldThrowExceptionOnDuplication() throws IOException {
        String modelsPath = "src/test/resources/models/";
        String registerUserPath = modelsPath + "user-register.json";
        RegisterUserDto duplicateDto =  new ObjectMapper().readValue(new File(registerUserPath), RegisterUserDto.class);
        assertThrows(DataIntegrityViolationException.class, () -> userService.registerUser(duplicateDto));
    }

    @Test
    void shouldThrowExceptionOnInvalidData(){
        registerUserDto.setAddress(new Address());
        registerUserDto.setEmail("Test@test.com");
        registerUserDto.setPhone("999999999999999999999");
        assertThrows(DataIntegrityViolationException.class, () -> userService.registerUser(registerUserDto));
    }
}
