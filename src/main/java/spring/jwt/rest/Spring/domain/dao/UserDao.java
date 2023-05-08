package spring.jwt.rest.Spring.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.jwt.rest.Spring.domain.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserDao extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
