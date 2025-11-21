package com.kuza.kuzasokoni.domain.loan.repositories;

import com.kuza.kuzasokoni.domain.loan.dtos.query.RepaymentScheduleView;
import com.kuza.kuzasokoni.domain.loan.entities.RepaymentSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface RepaymentScheduleRepository extends JpaRepository<RepaymentSchedule, Long> {

    @Query("""
        SELECT r.id AS id,
               r.installmentNumber AS installmentNumber,
               r.days AS days,
               r.expectedDate AS expectedDate,
               r.paidDate AS paidDate,
               r.principalDue AS principalDue,
               r.outstanding AS outstanding,
               r.remainingBalance AS remainingBalance,
               r.interest AS interest,
               r.fees AS fees,
               r.penalty AS penalty,
               r.totalDue AS totalDue,
               r.totalPaid AS totalPaid,
               r.lateBy AS lateBy,
               r.status AS status
        FROM RepaymentSchedule r
        WHERE r.loan.id = :loanId
        ORDER BY r.installmentNumber ASC
    """)
    Page<RepaymentScheduleView> findByLoanId(Long loanId, Pageable pageable);

    @Query("""
        SELECT r.id AS id,
               r.installmentNumber AS installmentNumber,
               r.days AS days,
               r.expectedDate AS expectedDate,
               r.paidDate AS paidDate,
               r.principalDue AS principalDue,
               r.outstanding AS outstanding,
               r.remainingBalance AS remainingBalance,
               r.interest AS interest,
               r.fees AS fees,
               r.penalty AS penalty,
               r.totalDue AS totalDue,
               r.totalPaid AS totalPaid,
               r.lateBy AS lateBy,
               r.status AS status
        FROM RepaymentSchedule r
        WHERE r.id = :id
    """)
    Optional<RepaymentScheduleView> findProjectedById(Long id);


    boolean existsByLoanId(Long loanId);


    @Query("SELECT r FROM RepaymentSchedule r WHERE r.status = 'PENDING' AND r.expectedDate < :today")
    Page<RepaymentSchedule> findAllPendingAndOverdue(@Param("today") LocalDate today, Pageable unpaged);

    @Modifying
    @Query("DELETE FROM RepaymentSchedule r WHERE r.loan.id = :loanId")
    void deleteAllByLoanId(@Param("loanId") Long loanId);

}
