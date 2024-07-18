package br.com.hr_system.config.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTokenException extends ResponseStatusException {
    public InvalidTokenException(){
        super(HttpStatus.UNAUTHORIZED, "O token enviado é inválido ou expirou ");
    }
}
