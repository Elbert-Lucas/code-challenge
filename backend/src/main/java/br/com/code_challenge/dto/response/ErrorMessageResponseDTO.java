package br.com.code_challenge.dto.response;

import br.com.code_challenge.dto.response.abstracts.MessageResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class ErrorMessageResponseDTO extends MessageResponseDTO {

    @JsonIgnore
    private Environment env;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String exception;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String location;


    public ErrorMessageResponseDTO(String message, HttpStatus status, String exception, String location, Environment env){
        super(message, status);
        if(env.matchesProfiles("dev")) {
            this.exception = exception;
            this.location = location;
        }else{
            this.exception = null;
            this.location = null;
        }
    }
}
