package com.kuza.kuzasokoni.common.authentication.repositories;

import com.kuza.kuzasokoni.common.authentication.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    }


