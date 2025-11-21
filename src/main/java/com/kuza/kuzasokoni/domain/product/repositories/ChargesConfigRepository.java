package com.kuza.kuzasokoni.domain.product.repositories;

import com.kuza.kuzasokoni.domain.product.entities.ChargesConfig;
import com.kuza.kuzasokoni.domain.product.dtos.query.ChargesConfigView;
import com.kuza.kuzasokoni.domain.product.enums.CollectedOn;
import com.kuza.kuzasokoni.domain.product.enums.RepaymentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


   // @Query("SELECT c FROM ChargesConfig c WHERE c.collectedOn = :collectedOn")
    //Page<ChargesConfigView> findByCollectedOn(CollectedOn collectedOn);

    //@Query("SELECT c FROM ChargesConfig c WHERE c.repaymentType = :repaymentType")
   // Page<ChargesConfigView> findByRepaymentType(RepaymentType repaymentType);

    //@Query("SELECT c FROM ChargesConfig c WHERE c.amount >= :minAmount")
    //Page<ChargesConfigView> findByAmountGreaterThan(BigDecimal minAmount);

    Page<ChargesConfigView> findAllBy(Pageable pageable);


}
