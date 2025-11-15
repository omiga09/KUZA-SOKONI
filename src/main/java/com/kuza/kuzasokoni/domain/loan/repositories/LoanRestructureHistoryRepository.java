package com.kuza.kuzasokoni.domain.loan.repositories;

import com.kuza.kuzasokoni.domain.loan.entities.LoanRestructureHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRestructureHistoryRepository extends JpaRepository<LoanRestructureHistory, Long> {

}
