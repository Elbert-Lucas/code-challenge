package br.com.hr_system.handler;

import br.com.hr_system.shared.dto.ErrorMessageResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    Environment env;

    @ExceptionHandler(value = ResponseStatusException.class)
    protected ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        final HttpStatus httpStatus = HttpStatus.valueOf(ex.getStatusCode().value());
        logError(ex);
        return new ResponseEntity<>(buildDTO(ex.getReason(),httpStatus, ex), httpStatus);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleExceptions(Exception ex) {
        ex.getStackTrace();
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ex.getMessage();
        if(!env.matchesProfiles("dev")) message = "Um erro inesperado ocorreu durante o processo";
        logError(ex);
        return new ResponseEntity<>(buildDTO(message,httpStatus, ex), httpStatus);
    }
    private ErrorMessageResponseDTO buildDTO(String message, HttpStatus httpStatus, Exception ex){
        return new ErrorMessageResponseDTO(message,
                                           httpStatus,
                                           ex.getClass().getSimpleName(),
                                           getLocation(ex),
                                           env);
    }
    private String getLocation(Exception ex){
        return Arrays.stream(ex.getStackTrace())
                      .filter(element -> element.getClassName().contains("br.com.hr_system"))
                      .findFirst()
                      .map(clazz -> clazz.getClassName().substring(clazz.getClassName().lastIndexOf(".") + 1)
                                    + ": " +
                                    clazz.getLineNumber())
                      .orElse(null);
    }
    private void logError(Exception ex){
        log.error("Exception {} thrown on: {}. Message received: {}",
                    ex.getClass().getSimpleName(), getLocation(ex), ex.getMessage());
    }
}
