package spring.jwt.rest.Spring.domain.dto;

import lombok.Value;

@Value
public class AuthenticationRequestDto {
    String username;
    String password;
}
