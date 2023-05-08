package spring.jwt.rest.Spring.domain.dto;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
@Value
public class UserCreateDto {
    @NotEmpty
    String username;
    @NotEmpty
    String firstName;
    @NotEmpty
    String lastName;
    @Email
    @NotEmpty
    String email;
    @NotEmpty
    String password;
    @NotEmpty
    String status;

}
