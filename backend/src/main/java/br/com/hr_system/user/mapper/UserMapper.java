package br.com.hr_system.user.mapper;

import br.com.hr_system.user.domain.Function;
import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.dto.LoggedUserDetailsDto;
import br.com.hr_system.user.dto.RegisterUserDto;
import br.com.hr_system.user.dto.UserBasicDetailsDto;
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
        user.setStatus(UserStatus.PASSWORD_PENDING);
        user.setRole(roleRepository.findByRole(UserRole.BASIC).get());
        return user;
    }
    public UserBasicDetailsDto entityToBasicDTO(User entity){
        UserBasicDetailsDto dto = new ModelMapper().map(entity, UserBasicDetailsDto.class);
        dto.setDepartment(entity.getDepartment().getName());
        dto.setFunction(entity.getFunction().getName());
        dto.setPhone(entity.getRole().getRole().name());
        return dto;
    }
    public LoggedUserDetailsDto entityToLoggedDTO(User entity){
        LoggedUserDetailsDto dto = new ModelMapper().map(entity, LoggedUserDetailsDto.class);
        dto.setRole(entity.getRole().getRole().name());
        dto.setDepartment(entity.getDepartment().getName());
        dto.setDepartment(entity.getFunction().getName());
        return dto;
    }
}
