package br.com.hr_system.user.repository;

import br.com.hr_system.user.dto.LoggedUserDetailsDto;
import br.com.hr_system.user.dto.UserBasicDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public class UserVanillaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String DEPARTMENT_QUERY = " LEFT JOIN TB_DEPARTMENT dep ON u.department_id = dep.id ";
    private final String FUNCTION_QUERY = " LEFT JOIN TB_FUNCTION func ON u.function_id = func.id ";
    private final String ROLE_QUERY = " LEFT JOIN TB_ROLE r ON u.role_id = r.id ";
    private final String MANAGER_QUERY = " LEFT JOIN TB_USER manager ON u.manager_id = manager.id ";

    private final String BASIC_USER_PARAMETERS = " u.id, u.name, u.email, u.phone, u.birth_dt as birth, dep.name as department, func.name as function, manager.name as manager ";
    private final String BASIC_USER_QUERY = " SELECT " + BASIC_USER_PARAMETERS + " FROM TB_USER u " + DEPARTMENT_QUERY + FUNCTION_QUERY + MANAGER_QUERY + " WHERE  u.id = ? ";

    private String LOGGED_USER_PARAMETERS = BASIC_USER_PARAMETERS + ", u.status, r.role as role ";
    private String LOGGED_USER_QUERY = " SELECT " + LOGGED_USER_PARAMETERS + " FROM TB_USER u" +  DEPARTMENT_QUERY + FUNCTION_QUERY + MANAGER_QUERY + ROLE_QUERY + " WHERE u.id = ? ";

    public Optional<UserBasicDetailsDto> findUserBasicDetails(Long id){
        return Optional.ofNullable(jdbcTemplate.queryForObject(BASIC_USER_QUERY,
                new BeanPropertyRowMapper<>(UserBasicDetailsDto.class),
                id
        ));
    }

    public Optional<LoggedUserDetailsDto> findLoggedUserDetailsById(Long id){
        return Optional.ofNullable(jdbcTemplate.queryForObject(LOGGED_USER_QUERY,
                                                               new BeanPropertyRowMapper<>(LoggedUserDetailsDto.class),
                                                               id));
    }
}