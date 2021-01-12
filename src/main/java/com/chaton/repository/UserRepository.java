package com.chaton.repository;

import com.chaton.web.config.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByUsername(String username);

    Optional<ApplicationUser> findByVerificationCode(String code);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);
}
