package com.kuza.kuzasokoni.domain.authentication.repositories;


import com.kuza.kuzasokoni.domain.authentication.entity.Otp;
import com.kuza.kuzasokoni.domain.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByUser(User user);
    Optional<Otp> findByUserAndCode(User user, String code);

}