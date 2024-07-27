package br.com.hr_system.user.mapper;

import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.domain.view.LoggedUserDetails;
import br.com.hr_system.user.domain.view.UserBasicDetails;
import br.com.hr_system.user.dto.RegisterUserDto;
import br.com.hr_system.user.dto.SimpleUserDto;
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
    public UserBasicDetails entityToBasicDTO(User entity){
        UserBasicDetails dto = new ModelMapper().map(entity, UserBasicDetails.class);
        dto.setDepartment(entity.getDepartment().getName());
        dto.setFunction(entity.getFunction().getName());
        dto.setPhone(entity.getRole().getRole().name());
        return dto;
    }
    public SimpleUserDto entityToSimpleDTO(User entity){
        SimpleUserDto dto = new ModelMapper().map(entity, SimpleUserDto.class);
        return dto;
    }
    public LoggedUserDetails entityToLoggedDTO(User entity){
        LoggedUserDetails dto = new ModelMapper().map(entity, LoggedUserDetails.class);
        dto.setRole(entity.getRole().getRole().name());
        dto.setDepartment(entity.getDepartment().getName());
        dto.setDepartment(entity.getFunction().getName());
        return dto;
    }
}
