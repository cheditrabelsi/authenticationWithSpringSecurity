package com.chedi.authentication.repositories;

import com.chedi.authentication.entities.User;
import com.chedi.authentication.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findFirstByEmail(String email);
    User findByRole(UserRole role);
}
