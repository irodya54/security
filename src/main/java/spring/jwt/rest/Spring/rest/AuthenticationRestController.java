package spring.jwt.rest.Spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import spring.jwt.rest.Spring.domain.dto.AuthenticationRequestDto;
import spring.jwt.rest.Spring.domain.dto.UserReadDto;
import spring.jwt.rest.Spring.domain.entities.Role;
import spring.jwt.rest.Spring.domain.entities.User;
import spring.jwt.rest.Spring.domain.mapper.UserMapper;
import spring.jwt.rest.Spring.domain.service.UserService;
import spring.jwt.rest.Spring.security.jwt.JwtTokenProvider;
import spring.jwt.rest.Spring.security.jwt.JwtUser;

import javax.security.sasl.AuthenticationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        String username = requestDto.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        UserReadDto user = userService.getUserByUsername(username);
        List<Role> roles = userService.findRolesByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с логином " + username + " не найден");
        }
        JwtUser jwtUser = UserMapper.INSTANCE.toJwtUser(user, roles);
        String token = jwtTokenProvider.createToken(jwtUser);

        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);

        return ResponseEntity.ok(response);

    }
}
