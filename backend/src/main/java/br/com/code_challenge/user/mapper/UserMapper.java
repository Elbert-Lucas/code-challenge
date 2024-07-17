package br.com.code_challenge.user.mapper;

import br.com.code_challenge.user.domain.Function;
import br.com.code_challenge.user.domain.User;
import br.com.code_challenge.user.dto.RegisterUserDto;
import br.com.code_challenge.user.enums.UserRole;
import br.com.code_challenge.user.enums.UserStatus;
import br.com.code_challenge.user.repository.RoleRepository;
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
}
