package com.kuza.kuzasokoni.domain.product.repositories;


import com.kuza.kuzasokoni.domain.product.dtos.query.TenureView;
import com.kuza.kuzasokoni.domain.product.entities.Tenure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TenureRepository extends JpaRepository<Tenure, Long> {

        List<Tenure> findByProductId(Long productId);

        // Projection using native SQL
        @Query(value = """
        SELECT 
            t.id AS id,
            t.penalty_group_one AS penaltyGroupOne,
            t.penalty_group_two AS penaltyGroupTwo,
            t.penalty_group_three AS penaltyGroupThree,
            t.penalty_group_four AS penaltyGroupFour,
            t.phase1_days AS phase1Days,
            t.phase2_days AS phase2Days,
            t.phase3_days AS phase3Days,
            t.phase4_days AS phase4Days,
            t.penalty_cap AS penaltyCap,
            t.grace_period_days AS gracePeriodDays,
            t.interest_min AS interestMin,
            t.interest_max AS interestMax,
            t.number_of_days AS numberOfDays,
            t.product_id AS productId
        FROM tenures t
        WHERE t.product_id = :productId
        """, nativeQuery = true)
        List<TenureView> findTenureViewsByProductId(Long productId);
    }

