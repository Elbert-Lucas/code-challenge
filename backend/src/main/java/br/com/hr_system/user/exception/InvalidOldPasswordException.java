package br.com.hr_system.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidOldPasswordException extends ResponseStatusException {
    public InvalidOldPasswordException(){
        super(HttpStatus.BAD_REQUEST, "A senha antiga não esta correta");
    }
}
