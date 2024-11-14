package br.com.hr_system.user.controller;

import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.dto.PasswordUpdateDto;
import br.com.hr_system.user.dto.RegisterUserDto;
import br.com.hr_system.shared.dto.abstracts.MessageResponseDTO;
import br.com.hr_system.user.service.UserUpdatesService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserUpdatesService userUpdatesService;

    @Autowired
    public UserController(UserUpdatesService userUpdatesService) {
        this.userUpdatesService = userUpdatesService;
    }

//    @RolesAllowed({"ROLE_OWNER", "ROLE_SUPER_ADMIN", "ROLE_ADMIN"})
    @PutMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponseDTO> registerUser(@RequestBody @Valid RegisterUserDto registerUserDto){
        log.debug("Iniciando registro de usuario");
        MessageResponseDTO response = userUpdatesService.registerUser(registerUserDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PatchMapping(value = "/change-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponseDTO> changePassword(@RequestBody PasswordUpdateDto passwordUpdateDto){
        log.debug("Iniciando update de senha do usuario");
        MessageResponseDTO response = userUpdatesService.updatePassword(passwordUpdateDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
