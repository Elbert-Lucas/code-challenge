package br.com.hr_system.shared.dto;

import br.com.hr_system.shared.dto.abstracts.MessageResponseDTO;
import org.springframework.http.HttpStatus;

public class OkMessageResponseDTO extends MessageResponseDTO {
    public OkMessageResponseDTO(String message){
        super(message, HttpStatus.OK);
    }
}
