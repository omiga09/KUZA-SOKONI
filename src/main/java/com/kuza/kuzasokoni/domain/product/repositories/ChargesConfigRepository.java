package com.kuza.kuzasokoni.domain.product.repositories;

import com.kuza.kuzasokoni.domain.product.entities.ChargesConfig;
import com.kuza.kuzasokoni.domain.product.dtos.query.ChargesConfigView;
import com.kuza.kuzasokoni.domain.product.enums.CollectedOn;
import com.kuza.kuzasokoni.domain.product.enums.RepaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChargesConfigRepository extends JpaRepository<ChargesConfig,Long> {

    @Query("SELECT c FROM ChargesConfig c WHERE c.id = :id")
    Optional<ChargesConfigView> findProjectedById(@Param("id") Long id);


    @Query("SELECT c FROM ChargesConfig c WHERE c.collectedOn = :collectedOn")
    List<ChargesConfigView> findByCollectedOn(CollectedOn collectedOn);

    @Query("SELECT c FROM ChargesConfig c WHERE c.repaymentType = :repaymentType")
    List<ChargesConfigView> findByRepaymentType(RepaymentType repaymentType);

    @Query("SELECT c FROM ChargesConfig c WHERE c.amount >= :minAmount")
    List<ChargesConfigView> findByAmountGreaterThan(BigDecimal minAmount);

    List<ChargesConfigView> findAllBy();



}
