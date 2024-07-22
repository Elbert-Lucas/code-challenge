package br.com.hr_system.unit.service;

import br.com.hr_system.user.domain.Address;
import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.dto.PasswordUpdateDto;
import br.com.hr_system.user.dto.RegisterUserDto;
import br.com.hr_system.user.enums.UserStatus;
import br.com.hr_system.user.exception.InvalidConfirmPassword;
import br.com.hr_system.user.exception.InvalidOldPassword;
import br.com.hr_system.user.mapper.UserMapper;
import br.com.hr_system.user.repository.UserRepository;
import br.com.hr_system.user.service.UserDetailsService;
import br.com.hr_system.user.service.UserUpdatesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class UserUpdatesServiceUT {

    @Autowired
    UserUpdatesService userUpdatesService;

    @Autowired
    UserRepository userRepository;

    @MockBean
    UserDetailsService userDetailsService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    static RegisterUserDto registerUserDto;

    @BeforeAll
    static void setup() throws IOException {
        String modelsPath = "src/test/resources/models/";
        String registerUserPath = modelsPath + "user-register.json";
        registerUserDto =  new ObjectMapper().readValue(new File(registerUserPath), RegisterUserDto.class);
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
    void shouldRegisterUserSuccessfully(){
        assertEquals(HttpStatus.CREATED, userUpdatesService.registerUser(registerUserDto).getStatus());
    }

    @Test
    void shouldThrowExceptionOnDuplication() throws IOException {
        userUpdatesService.registerUser(registerUserDto);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> userUpdatesService.registerUser(registerUserDto));
    }

    @Test
    void shouldUpdatePasswordSuccessfully() throws IOException {
        userUpdatesService.registerUser(registerUserDto);
        User user = userRepository.findUserByEmail(registerUserDto.getEmail()).get();
        when(userDetailsService.findLoggedUser()).thenReturn(user);

        PasswordUpdateDto passwordDto = new ObjectMapper().readValue(new File("src/test/resources/models/change-password.json"), PasswordUpdateDto.class);
        assertEquals(HttpStatus.OK, userUpdatesService.updatePassword(passwordDto).getStatus());
    }
    @Test
    void shouldThrowExceptionOnInvalidOldPassword() throws IOException {
        userUpdatesService.registerUser(registerUserDto);
        User user = userRepository.findUserByEmail(registerUserDto.getEmail()).get();
        user.setStatus(UserStatus.ACTIVE);
        when(userDetailsService.findLoggedUser()).thenReturn(user);

        PasswordUpdateDto passwordDto = new ObjectMapper().readValue(new File("src/test/resources/models/change-password.json"), PasswordUpdateDto.class);
        assertThrows(InvalidOldPassword.class, () -> userUpdatesService.updatePassword(passwordDto).getStatus());
    }
    @Test
    void shouldThrowExceptionOnInvalidConfirmPassword() throws IOException {
        userUpdatesService.registerUser(registerUserDto);
        User user = userRepository.findUserByEmail(registerUserDto.getEmail()).get();
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode("1234567aA#"));
        when(userDetailsService.findLoggedUser()).thenReturn(user);

        PasswordUpdateDto passwordDto = new ObjectMapper().readValue(new File("src/test/resources/models/change-password.json"), PasswordUpdateDto.class);
        passwordDto.setConfirmPassword("Confirm");
        assertThrows(InvalidConfirmPassword.class, () -> userUpdatesService.updatePassword(passwordDto).getStatus());
    }
}
