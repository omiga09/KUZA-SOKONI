package com.kuza.kuzasokoni.domain.product.repositories;

import com.kuza.kuzasokoni.domain.product.dtos.query.RepaymentStrategyView;
import com.kuza.kuzasokoni.domain.product.entities.RepaymentStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepaymentStrategyRepository extends JpaRepository<RepaymentStrategy, Long> {

    @Query("SELECT r FROM RepaymentStrategy r")
    List<RepaymentStrategyView> findAllProjected();

    @Query("SELECT r FROM RepaymentStrategy r WHERE r.id = :id")
    Optional<RepaymentStrategyView> findProjectedById(Long id);
}
