package br.com.hr_system.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidConfirmPasswordException extends ResponseStatusException {
    public InvalidConfirmPasswordException(){
        super(HttpStatus.BAD_REQUEST, "As senhas enviadas não conferem");
    }
}