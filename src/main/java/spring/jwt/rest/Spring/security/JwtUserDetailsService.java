package spring.jwt.rest.Spring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import spring.jwt.rest.Spring.domain.dto.UserReadDto;
import spring.jwt.rest.Spring.domain.service.UserService;
import spring.jwt.rest.Spring.security.jwt.JwtUser;
import spring.jwt.rest.Spring.security.jwt.JwtUserFactory;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserReadDto user = userService.getUserByUsername(username);

        if (user != null) {
            throw new UsernameNotFoundException("Юзер " + username + " не найден");
        }

        JwtUser jwtUser = JwtUserFactory.createUser(user, userService.findRolesByUserName(user.getUsername()));
        log.info("В методе loadUserByUsername получен JwtUser {}", jwtUser);
        return jwtUser;
    }
}
