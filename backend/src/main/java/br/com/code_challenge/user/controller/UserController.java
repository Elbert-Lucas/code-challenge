package br.com.code_challenge.user.controller;

import br.com.code_challenge.user.domain.User;
import br.com.code_challenge.user.dto.RegisterUserDto;
import br.com.code_challenge.shared.dto.abstracts.MessageResponseDTO;
import br.com.code_challenge.user.service.UserService;
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

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponseDTO> registerUser(@RequestBody @Valid RegisterUserDto registerUserDto){
        log.debug("Iniciando registro de usuario");
        MessageResponseDTO response = userService.registerUser(registerUserDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> searchUser(@PathVariable(name = "id") Long userId){
        return new ResponseEntity<>(userService.searchUser(userId), HttpStatus.OK);
    }

}
