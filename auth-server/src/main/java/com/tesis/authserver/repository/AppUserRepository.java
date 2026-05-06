package com.tesis.authserver.repository;

import com.tesis.authserver.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Integer> {
    Optional<AppUser> findByUsername(String username);
}
