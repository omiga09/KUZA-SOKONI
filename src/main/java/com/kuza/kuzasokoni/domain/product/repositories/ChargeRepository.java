package com.kuza.kuzasokoni.domain.product.repositories;

import com.kuza.kuzasokoni.domain.product.dtos.query.ChargeView;
import com.kuza.kuzasokoni.domain.product.entities.Charge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChargeRepository extends JpaRepository<Charge,Long> {

        @Query("SELECT c FROM Charge c")
        List<ChargeView> findAllProjected();

        @Query("SELECT c FROM Charge c WHERE c.id = :id")
        Optional<ChargeView> findProjectedById(Long id);


}

