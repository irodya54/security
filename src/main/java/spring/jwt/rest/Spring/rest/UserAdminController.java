package spring.jwt.rest.Spring.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.jwt.rest.Spring.domain.dto.UserReadDto;
import spring.jwt.rest.Spring.domain.service.UserService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity getUser(@PathVariable UUID id) {
        UserReadDto userById = userService.getUserById(id);
        return ResponseEntity.ok(userById);
    }

    @GetMapping("/users/test")
    public ResponseEntity test() {
        return ResponseEntity.ok("test");
    }
}
