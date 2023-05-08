package spring.jwt.rest.Spring.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import spring.jwt.rest.Spring.domain.dto.UserCreateDto;
import spring.jwt.rest.Spring.domain.dto.UserReadDto;
import spring.jwt.rest.Spring.domain.entities.Role;
import spring.jwt.rest.Spring.domain.entities.User;
import spring.jwt.rest.Spring.security.jwt.JwtUser;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserReadDto toUserReadDto(User user);
    User toUser(UserReadDto dto);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "getRoles")
    @Mapping(target = "id", source = "readDto.id")
    @Mapping(target = "username", source = "readDto.username")
    @Mapping(target = "lastName", source = "readDto.lastName")
    @Mapping(target = "firstName", source = "readDto.firstName")
    @Mapping(target = "email", source = "readDto.email")
    @Mapping(target = "password", source = "readDto.password")
    @Mapping(target = "status", source = "readDto.status")
    JwtUser toJwtUser(UserReadDto readDto, Collection<Role> roles);

    @Named("getRoles")
    static Collection<? extends GrantedAuthority> getRoles(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }


    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toUser(UserCreateDto dto, UUID id);
}
