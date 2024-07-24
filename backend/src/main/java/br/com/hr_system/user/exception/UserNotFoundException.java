package br.com.hr_system.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
    public UserNotFoundException(){
        super(HttpStatus.NOT_FOUND, "O usuário requisitado não foi encontrado");
    }
}