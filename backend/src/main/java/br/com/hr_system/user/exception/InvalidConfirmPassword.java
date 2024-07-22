package br.com.hr_system.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidConfirmPassword extends ResponseStatusException {
    public InvalidConfirmPassword(){
        super(HttpStatus.BAD_REQUEST, "As senhas enviadas não conferem");
    }
}