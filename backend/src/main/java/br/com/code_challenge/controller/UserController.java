package br.com.code_challenge.controller;

import br.com.code_challenge.dto.request.RegisterUserDTO;
import br.com.code_challenge.dto.response.abstracts.MessageResponseDTO;
import br.com.code_challenge.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponseDTO> registerUser(@RequestBody @Valid RegisterUserDTO registerUserDTO){
        log.debug("Iniciando registro de usuario");
        MessageResponseDTO response = userService.registerUser(registerUserDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
