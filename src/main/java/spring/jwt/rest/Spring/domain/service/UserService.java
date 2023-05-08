package spring.jwt.rest.Spring.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.jwt.rest.Spring.domain.dao.RoleDao;
import spring.jwt.rest.Spring.domain.dao.UserDao;
import spring.jwt.rest.Spring.domain.dto.UserCreateDto;
import spring.jwt.rest.Spring.domain.dto.UserReadDto;
import spring.jwt.rest.Spring.domain.entities.Role;
import spring.jwt.rest.Spring.domain.entities.Status;
import spring.jwt.rest.Spring.domain.entities.User;
import spring.jwt.rest.Spring.domain.mapper.UserMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserReadDto getUserByUsername(String username) {
        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return UserMapper.INSTANCE.toUserReadDto(user);
    }

    @Transactional
    public List<UserReadDto> getAllUsers() {
        return userDao.findAll().stream()
                .map(UserMapper.INSTANCE::toUserReadDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserReadDto getUserById(UUID id) {
        Optional<User> mayBeUser = userDao.findById(id);
        if (mayBeUser.isPresent()) {
            return UserMapper.INSTANCE.toUserReadDto(mayBeUser.get());
        }
        log.warn("Юзер с ИД {} не найден", id);
        return null;
    }

    @Transactional
    public UserReadDto register(UserCreateDto userCreateDto) {
        Role userRole = roleDao.findByName("ROLE_USER");
        List<Role> roles = Collections.singletonList(userRole);

        User user = UserMapper.INSTANCE.toUser(userCreateDto, UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user.setRoles(roles);
        user.setStatus(Status.ACTIVE);

        log.info("Юзер {} зарегистрирован", user.getUsername());
        return UserMapper.INSTANCE.toUserReadDto(userDao.save(user));
    }

    @Transactional
    public void delete(UserReadDto userReadDto) {
        userDao.deleteById(userReadDto.getId());
        log.info("Юзер {} удален", userReadDto.getUsername());
    }

    public List<Role> findRolesByUserName(String username) {
        return userDao.findByUsername(username)
                .map(user -> user.getRoles())
                .orElse(Collections.emptyList());
    }
}
