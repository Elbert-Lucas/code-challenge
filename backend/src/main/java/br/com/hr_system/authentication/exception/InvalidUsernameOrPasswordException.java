package br.com.hr_system.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidUsernameOrPasswordException extends ResponseStatusException {
    public InvalidUsernameOrPasswordException(){
        super(HttpStatus.UNAUTHORIZED, "Usuario ou senha estao incorretos");
    }
}
