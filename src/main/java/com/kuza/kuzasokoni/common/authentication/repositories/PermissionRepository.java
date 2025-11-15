package com.kuza.kuzasokoni.common.authentication.repositories;


import com.kuza.kuzasokoni.common.authentication.entity.AppPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Permission;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<AppPermission, Long> {
    Optional<AppPermission> findByName(String name);
}