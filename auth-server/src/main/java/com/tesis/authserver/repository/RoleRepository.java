package com.tesis.authserver.repository;

import com.tesis.authserver.entity.Role;
import com.tesis.authserver.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByRole(RoleName roleName);
}
