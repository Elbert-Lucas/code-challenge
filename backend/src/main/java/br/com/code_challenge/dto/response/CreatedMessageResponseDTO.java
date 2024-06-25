package br.com.code_challenge.dto.response;

import br.com.code_challenge.dto.response.abstracts.MessageResponseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class CreatedMessageResponseDTO extends MessageResponseDTO {
    public CreatedMessageResponseDTO(String message){
        super(message, HttpStatus.CREATED);
    }

}
