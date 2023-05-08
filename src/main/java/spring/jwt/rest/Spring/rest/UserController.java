package spring.jwt.rest.Spring.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.jwt.rest.Spring.domain.dto.UserCreateDto;
import spring.jwt.rest.Spring.domain.dto.UserReadDto;
import spring.jwt.rest.Spring.domain.service.UserService;
@Slf4j
@RestController
@RequestMapping("/api/v1/register")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity register(@RequestBody UserCreateDto userCreateDto) {
        UserReadDto register = userService.register(userCreateDto);
        if (register != null) {
            log.info("Пользователь с логином " + userCreateDto.getUsername() + "зарегистрирован");
            return ResponseEntity.ok(register);
        }
        return ResponseEntity.badRequest().body("Пользователь не зарегистрирован");
    }
}
