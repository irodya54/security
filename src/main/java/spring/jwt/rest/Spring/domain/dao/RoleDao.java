package spring.jwt.rest.Spring.domain.dao;

import liquibase.pro.packaged.R;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.jwt.rest.Spring.domain.entities.Role;

import java.util.UUID;

public interface RoleDao extends JpaRepository<Role, UUID> {

    public Role findByName(String name);
}
