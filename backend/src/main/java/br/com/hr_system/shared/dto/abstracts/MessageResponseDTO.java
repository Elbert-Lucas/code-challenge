package br.com.hr_system.shared.dto.abstracts;


import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@MappedSuperclass
@Getter @Setter
@AllArgsConstructor
public abstract class MessageResponseDTO {
     protected final String message;
     protected final HttpStatus status;
}
