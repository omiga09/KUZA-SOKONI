package com.kuza.kuzasokoni.domain.loan.repositories;

import com.kuza.kuzasokoni.domain.loan.dtos.query.RepaymentScheduleView;
import com.kuza.kuzasokoni.domain.loan.entities.RepaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RepaymentScheduleRepository extends JpaRepository<RepaymentSchedule, Long> {

    @Query("SELECT r FROM RepaymentSchedule r WHERE r.loan.id = :loanId")
    List<RepaymentScheduleView> findByLoanId(Long loanId);

    @Query("SELECT r.id AS id, r.days AS days, r.expectedDate AS expectedDate, r.paidDate AS paidDate, r.principalDue AS principalDue, r.outstanding AS outstanding, r.interest AS interest, r.fees AS fees, r.penalty AS penalty, r.totalDue AS totalDue, r.totalPaid AS totalPaid, r.lateBy AS lateBy, r.status AS status FROM RepaymentSchedule r WHERE r.id = :id")
    Optional<RepaymentScheduleView> findProjectedById(Long id);

}

