package com.todoback.todobackend.repository;

import com.todoback.todobackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByActivationId(String activationId);
    Optional<User> findByEmail(String email);
    Optional<User> findByPasswordChangeId(String passwordChangeId);
    Optional<User> findByLolUsername(String lolUsername);

}
