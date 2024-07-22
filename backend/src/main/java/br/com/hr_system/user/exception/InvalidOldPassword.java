package br.com.hr_system.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidOldPassword extends ResponseStatusException {
    public InvalidOldPassword(){
        super(HttpStatus.BAD_REQUEST, "A senha antiga não esta correta");
    }
}
