package br.com.hr_system.shared.dto;

import br.com.hr_system.shared.dto.abstracts.MessageResponseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class CreatedMessageResponseDTO extends MessageResponseDTO {
    public CreatedMessageResponseDTO(String message){
        super(message, HttpStatus.CREATED);
    }

}
