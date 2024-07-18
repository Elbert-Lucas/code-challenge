package br.com.hr_system.user.mapper;

import br.com.hr_system.user.domain.Function;
import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.dto.LoggedUserDetailsDto;
import br.com.hr_system.user.dto.RegisterUserDto;
import br.com.hr_system.user.enums.UserRole;
import br.com.hr_system.user.enums.UserStatus;
import br.com.hr_system.user.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public User dtoToEntity(RegisterUserDto dto) {
        User user = new ModelMapper().map(dto, User.class);
        user.setStatus(UserStatus.ACTIVE);
        user.setRole(roleRepository.findByRole(UserRole.BASIC).get());
        return user;
    }
    public LoggedUserDetailsDto entityToLoggedDTO(User entity){
        LoggedUserDetailsDto dto = new ModelMapper().map(entity, LoggedUserDetailsDto.class);
        dto.setRole(entity.getRole().getRole().name());
        return dto;
    }
}
