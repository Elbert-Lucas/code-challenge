package br.com.code_challenge.shared.dto;

import br.com.code_challenge.shared.dto.abstracts.MessageResponseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class CreatedMessageResponseDTO extends MessageResponseDTO {
    public CreatedMessageResponseDTO(String message){
        super(message, HttpStatus.CREATED);
    }

}
