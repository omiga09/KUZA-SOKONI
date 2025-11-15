package com.kuza.kuzasokoni.common.authentication.repositories;


import com.kuza.kuzasokoni.common.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
