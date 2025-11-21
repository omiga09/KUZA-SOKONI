package com.kuza.kuzasokoni.domain.authentication.repositories;


import com.kuza.kuzasokoni.domain.authentication.entity.AppPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<AppPermission, Long> {
    Optional<AppPermission> findByName(String name);
}