package com.chaton.repository;

import com.chaton.web.config.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByUsername(String email);

    Optional<ApplicationUser> findByVerificationCode(String code);

    Optional<ApplicationUser> findByResetPasswordToken(String token);

    void deleteByUsername(String email);

    boolean existsByUsername(String email);
}
