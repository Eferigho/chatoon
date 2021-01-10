package com.chaton.repository;

import com.chaton.web.config.ApplicationRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRoleRepository extends JpaRepository <ApplicationRole, Long> {
}
