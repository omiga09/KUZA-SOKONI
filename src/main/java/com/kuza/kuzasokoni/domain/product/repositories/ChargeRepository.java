package com.kuza.kuzasokoni.domain.product.repositories;

import com.kuza.kuzasokoni.domain.product.dtos.query.ChargeView;
import com.kuza.kuzasokoni.domain.product.entities.Charge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChargeRepository extends JpaRepository<Charge, Long> {

    @Query("""
        SELECT 
            c.id AS id,
            c.name AS name,
            c.amount AS amount,
            c.repaymentType AS repaymentType,
            c.collectedOn AS collectedOn,
            c.isPaid AS isPaid,
            c.paidAmount AS paidAmount,
            c.remainingAmount AS remainingAmount,
            c.dueDate AS dueDate,
            c.product AS product
        FROM Charge c
    """)
    Page<ChargeView> findAllProjected(Pageable pageable);

    @Query("""
    SELECT 
        c.id AS id,
        c.name AS name,
        c.amount AS amount,
        c.repaymentType AS repaymentType,
        c.collectedOn AS collectedOn,
        c.isPaid AS isPaid,
        c.paidAmount AS paidAmount,
        c.remainingAmount AS remainingAmount,
        c.dueDate AS dueDate,
        c.product AS product
    FROM Charge c
    WHERE c.id = :id
""")
    Optional<ChargeView> findProjectedById(Long id);

}
