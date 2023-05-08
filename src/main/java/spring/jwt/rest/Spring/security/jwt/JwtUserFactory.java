package spring.jwt.rest.Spring.security.jwt;

import lombok.experimental.UtilityClass;
import spring.jwt.rest.Spring.domain.dto.UserReadDto;
import spring.jwt.rest.Spring.domain.entities.Role;
import spring.jwt.rest.Spring.domain.mapper.UserMapper;

import java.util.Collection;

@UtilityClass
public final class JwtUserFactory {

    public static JwtUser createUser(UserReadDto userReadDto, Collection<Role> roles) {
        return UserMapper.INSTANCE.toJwtUser(userReadDto, roles);
    }

}
