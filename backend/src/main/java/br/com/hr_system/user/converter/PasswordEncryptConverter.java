package br.com.hr_system.user.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Converter
public class PasswordEncryptConverter implements AttributeConverter<String, String> {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Override
    public String convertToDatabaseColumn(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public String convertToEntityAttribute(String password) {
        return null;
    }
}
