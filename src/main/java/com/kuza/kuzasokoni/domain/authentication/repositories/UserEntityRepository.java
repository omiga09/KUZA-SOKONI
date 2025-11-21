package com.kuza.kuzasokoni.domain.authentication.repositories;

import com.kuza.kuzasokoni.domain.authentication.entity.Role;
import com.kuza.kuzasokoni.domain.authentication.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAllByRolesContaining(Role role);
    List<UserEntity> findByUserId(Long userId);
    }


