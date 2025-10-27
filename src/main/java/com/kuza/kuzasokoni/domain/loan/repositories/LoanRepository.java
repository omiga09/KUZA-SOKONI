package com.kuza.kuzasokoni.domain.loan.repositories;

import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.loan.dtos.query.LoanRepaymentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("""
    SELECT l.id AS id,
           l.principal AS principal,
           l.outstanding AS outstanding,
           l.interest AS interest,
           CONCAT(c.firstName, ' ', c.secondName, ' ', c.lastName) AS clientName
    FROM Loan l
    JOIN l.client c
    WHERE l.status = 'DISBURSED'
""")
    List<LoanRepaymentView> findAllRepaymentViews();

    @Query("""
    SELECT l.id AS id,
           l.outstanding AS outstanding,
           l.principal AS principal,
           l.interest AS interest,
           CONCAT(c.firstName, ' ', c.secondName, ' ', c.lastName) AS clientName
    FROM Loan l
    JOIN l.client c
    WHERE l.id = :loanId
""")
    Optional<LoanRepaymentView> findRepaymentViewById(@Param("loanId") Long loanId);

    @Query("SELECT l FROM Loan l JOIN FETCH l.repaymentSchedules")
    List<Loan> findAllWithSchedules();


}
